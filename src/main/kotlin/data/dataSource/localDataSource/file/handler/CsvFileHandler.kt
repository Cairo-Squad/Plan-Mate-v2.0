package data.dataSource.localDataSource.file.handler

import data.customException.PlanMateException
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.*

abstract class CsvFileHandler<DTO>(
    filePath: String,
    private val columnNames: List<String>,
    private val getDtoId: (DTO) -> UUID
) : FileHandler<DTO> {

    private val file: File = File(filePath)

    init {
        if (!file.exists()) {
            FileWriter(file, true).use { writer ->
                writer.appendLine(columnNames.joinToString(","))
            }
        }
    }

    abstract fun fromDtoToCsvRow(entity: DTO): String
    abstract fun fromCsvRowToDto(row: String): DTO

    override fun write(entity: DTO): Boolean {
        val numberOfRowBeforeWriter = file.readLines().size
        val newRow = fromDtoToCsvRow(entity)
        BufferedWriter(FileWriter(file, true)).use { writer ->
            writer.appendLine(newRow)
        }
        val numberOfRowAfterWriter = file.readLines().size
        return numberOfRowAfterWriter > numberOfRowBeforeWriter
    }

    private fun writeAll(entities: List<DTO>) {
        BufferedWriter(FileWriter(file, false)).use { writer ->
            writer.appendLine(columnNames.joinToString(","))
            entities.forEach { entity ->
                writer.appendLine(fromDtoToCsvRow(entity))
            }
        }
    }


    override fun edit(entity: DTO): Boolean {
        val entityId = getDtoId(entity)
        val allEntities = readAll()

        val index = allEntities.indexOfFirst { getDtoId(it) == entityId }
        if (index == -1) return false

        val updatedEntities = allEntities.toMutableList().apply {
            this[index] = entity
        }
        writeAll(updatedEntities)
        return true
    }

    override fun readAll(): List<DTO> {
        return file.readLines()
            .asSequence()
            .drop(1)
            .filter { it.isNotBlank() }
            .map { fromCsvRowToDto(it) }
            .toList()
    }

    override fun delete(entity: DTO): Boolean {
        val allEntities = readAll()
        if (allEntities.none { getDtoId(it) == getDtoId(entity) }) {
            return false
        }
        val updatedEntities = allEntities.filter { getDtoId(it) != getDtoId(entity) }
        writeAll(updatedEntities)
        return true
    }
}