package data.repositories.mappers

import data.dto.ProjectDto
import logic.model.Project
import logic.model.State
import logic.model.Task
import java.util.*

fun ProjectDto.toProject(projectTasks: List<Task>, projectState: State): Project {
    return Project(
        id = this.id,
        title = this.title,
        description = this.description,
        createdBy = this.createdBy,
        tasks = projectTasks,
        state = projectState
    )
}

fun Project.toProjectDto(): ProjectDto {
	return ProjectDto(
		id = this.id?: UUID.randomUUID(),
		title = this.title ?: "",
		description = this.description ?: "",
		createdBy = this.createdBy!!,
		taskIds = this.tasks?.map { it.id?: UUID.randomUUID() }?: emptyList(),
		stateId = this.state?.id!!
	)
}