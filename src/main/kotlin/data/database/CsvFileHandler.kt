package data.database

import logic.exception.DtoNotFoundException
import logic.exception.UserNotChangedException
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.*

abstract class CsvFileHandler<DTO>(
    filePath: String,
    private val headers: List<String>,
    private val getDtoId: (DTO) -> UUID
) : FileHandler<DTO> {

    private val file: File = File(filePath)

    init {
        if (!file.exists()) {
            FileWriter(file, true).use { writer ->
                writer.appendLine(headers.joinToString(","))
            }
        }
    }

    override fun write(entity: DTO) {
        val newRow = fromDtoToCsvRow(entity)
        BufferedWriter(FileWriter(file, true)).use { writer ->
            writer.appendLine(newRow)
        }
    }

    private fun writeAll(entities: List<DTO>) {
        BufferedWriter(FileWriter(file, false)).use { writer ->
            writer.appendLine(headers.joinToString(","))
            entities.forEach { entity ->
                writer.appendLine(fromDtoToCsvRow(entity))
            }
        }
    }

    override fun readAll(): List<DTO> {
        return file.readLines()
            .asSequence()
            .drop(1)
            .filter { it.isNotBlank() }
            .map { fromCsvRowToDto(it) }
            .toList()
    }

    override fun edit(entity: DTO) {
        val entityId = getDtoId(entity)
        val allEntities = readAll()

        val index = allEntities.indexOfFirst { getDtoId(it) == entityId }
        if (index == -1) throw DtoNotFoundException()

        val updatedEntities = allEntities.toMutableList().apply {
            this[index] = entity
        }
        writeAll(updatedEntities)
    }

    override fun delete(entity: DTO) {
        val allEntities = readAll().filter { currentEntity ->
            getDtoId(currentEntity) != getDtoId(entity)
        }
        writeAll(allEntities)
    }

    abstract fun fromDtoToCsvRow(entity: DTO): String

    abstract fun fromCsvRowToDto(row: String): DTO
}