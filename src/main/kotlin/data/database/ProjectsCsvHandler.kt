package data.database

import data.dto.ProjectDto

class ProjectsCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<ProjectDto>(
    filePath = filePath,
    headers = headers,
    getDtoId = { it.id }
) {
    override fun fromDtoToCsvRow(entity: ProjectDto): String {
        return ""
    }

    override fun fromCsvRowToDto(row: String): ProjectDto {
        return ProjectDto()
    }
}