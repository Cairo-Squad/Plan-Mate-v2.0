package data.database

import data.database.util.CsvIndices
import data.dto.TaskDto
import java.util.*

class TasksCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<TaskDto>(
    filePath = filePath,
    headers = headers,
    getDtoId = { it.id }
) {
    override fun fromDtoToCsvRow(entity: TaskDto): String {
        val rowStringBuilder = StringBuilder()
        rowStringBuilder.append("${entity.id}")
        rowStringBuilder.append(",${entity.title}")
        rowStringBuilder.append(",${entity.description}")
        rowStringBuilder.append(",${entity.stateId}")
        rowStringBuilder.append(",${entity.projectId}")
        return rowStringBuilder.toString()
    }

    override fun fromCsvRowToDto(row: String): TaskDto {
        val taskData = row.split(",")
        return TaskDto(
            id = UUID.fromString(taskData[CsvIndices.TASK_ID]),
            title = taskData[CsvIndices.TASK_TITLE],
            description = taskData[CsvIndices.TASK_DESCRIPTION],
            stateId = UUID.fromString(taskData[CsvIndices.TASK_STATE_ID]),
            projectId = UUID.fromString(taskData[CsvIndices.TASK_PROJECT_ID])
        )
    }
}