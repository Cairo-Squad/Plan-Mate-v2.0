package data.repositories.mappers

import data.dto.ProjectDto
import logic.model.Project
import logic.model.State
import logic.model.Task
import java.util.*

fun ProjectDto.toProject(projectState: State): Project {
    return Project(
        id = this.id,
        title = this.title,
        description = this.description,
        createdBy = this.createdBy,
        state = projectState
    )
}

fun Project.toProjectDto(): ProjectDto {
	return ProjectDto(
		id = this.id,
		title = this.title ?: "",
		description = this.description ?: "",
		createdBy = this.createdBy!!,
		stateId = this.state?.id!!
	)
}