package data.database

import logic.exception.DtoNotFoundException
import logic.exception.UserNotChangedException
import logic.exceptions.EntityNotFoundException
import logic.exceptions.CsvReadException
import logic.exception.CsvWriteException
import logic.exception.UnknownException
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
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
        try {
            val newRow = fromDtoToCsvRow(entity)
            BufferedWriter(FileWriter(file, true)).use { writer ->
                writer.appendLine(newRow)
            }
        } catch (e: IOException) {
            throw CsvWriteException()
        } catch (e: Exception) {
            throw UnknownException()
        }
    }

    private fun writeAll(entities: List<DTO>) {
        try {
            BufferedWriter(FileWriter(file, false)).use { writer ->
                writer.appendLine(headers.joinToString(","))
                entities.forEach { entity ->
                    writer.appendLine(fromDtoToCsvRow(entity))
                }
            }
        } catch (e: IOException) {
            throw CsvWriteException()
        } catch (e: Exception) {
            throw UnknownException()
        }
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

    override fun readAll(): List<DTO> {
        return try {
            file.readLines()
                .asSequence()
                .drop(1)
                .filter { it.isNotBlank() }
                .map { fromCsvRowToDto(it) }
                .toList()
        } catch (e: IOException) {
            throw CsvReadException()
        } catch (e: Exception) {
            throw UnknownException()
        }
    }

    override fun delete(entity: DTO) {
        try {
            val allEntities = readAll()
            if (allEntities.none { getDtoId(it) == getDtoId(entity) }) {
                throw EntityNotFoundException()
            }
            val updatedEntities = allEntities.filter { getDtoId(it) != getDtoId(entity) }
            writeAll(updatedEntities)
        } catch (e: EntityNotFoundException) {
            println("Error: ${e.message}")
        } catch (e: IOException) {
            throw CsvWriteException()
        } catch (e: Exception) {
            throw UnknownException()
        }
    }

    abstract fun fromDtoToCsvRow(entity: DTO): String

    abstract fun fromCsvRowToDto(row: String): DTO
}