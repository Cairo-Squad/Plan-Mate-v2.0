package data.database

import data.database.util.CsvIndices
import data.dto.ProjectDto
import java.util.*

class
ProjectsCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<ProjectDto>(
    filePath = filePath,
    headers = headers,
    getDtoId = { it.id }
) {
    override fun fromDtoToCsvRow(entity: ProjectDto): String {
        val rowStringBuilder = StringBuilder()
        rowStringBuilder.append("${entity.id}")
        rowStringBuilder.append(",${entity.title}")
        rowStringBuilder.append(",${entity.description}")
        rowStringBuilder.append(",${entity.createdBy}")
        rowStringBuilder.append(",${entity.tasks.joinToString("||")}}")
        rowStringBuilder.append(",${entity.stateId}}")
        return rowStringBuilder.toString()
    }
    
    override fun fromCsvRowToDto(row: String): ProjectDto {
        val projectData = row.split(",")
        return ProjectDto(
            id = UUID.fromString(projectData[CsvIndices.PROJECT_ID]),
            title = projectData[CsvIndices.PROJECT_TITLE],
            description = projectData[CsvIndices.PROJECT_DESCRIPTION],
            createdBy = UUID.fromString(projectData[CsvIndices.PROJECT_CREATED_BY]),
            tasks = projectData[CsvIndices.PROJECT_TASKS].split("||").map { UUID.fromString(it) },
            stateId = UUID.fromString(projectData[CsvIndices.PROJECT_STATE_ID])
        )
    }
}