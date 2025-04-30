package data.database

import data.database.util.CsvIndices
import data.dto.ProjectDto
import java.util.*

class ProjectsCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<ProjectDto>(
    filePath = filePath,
    headers = headers,
    getDtoId = { it.id }
) {

    private fun updateProjectTitle(projectId: UUID, newTitle: String) {
        val allProjects = readAll().map { project ->
            if (project.id == projectId) project.copy(title = newTitle) else project
        }
        writeAll(allProjects)
    }

    private fun updateProjectDescription(projectId: UUID, newDescription: String) {
        val allProjects = readAll().map { project ->
            if (project.id == projectId) project.copy(description = newDescription) else project
        }
        writeAll(allProjects)
    }

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

    override fun updateValue(id: UUID, newValue: String, type: AttributeToBeChanged) {
        when (type) {
            AttributeToBeChanged.TITLE -> updateProjectTitle(id, newValue)
            AttributeToBeChanged.DESCRIPTION -> updateProjectDescription(id, newValue)
            else -> throw IllegalArgumentException("only allowed to update project title and description")
        }
    }
}