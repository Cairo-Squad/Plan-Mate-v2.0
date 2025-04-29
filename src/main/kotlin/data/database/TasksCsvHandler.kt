package data.database

import data.dto.TaskDto

class TasksCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<TaskDto>(
    filePath = filePath,
    headers = headers,
    getDtoId = { it.id }
) {
    override fun fromDtoToCsvRow(entity: TaskDto): String {
        return ""
    }

    override fun fromCsvRowToDto(row: String): TaskDto {
        return TaskDto()
    }
}