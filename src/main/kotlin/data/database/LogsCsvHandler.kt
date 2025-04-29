package data.database

import data.dto.EntityType
import data.dto.LogDto

class LogsCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<LogDto>(
    filePath = filePath,
    headers = headers,
    getDtoId = { it.id }
) {

    override fun fromDtoToCsvRow(entity: LogDto): String {
        return "${entity.dateTime}"
    }

    override fun fromCsvRowToDto(row: String): LogDto {
        return LogDto(entityType = EntityType.TASK)
    }
}