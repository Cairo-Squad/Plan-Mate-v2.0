package data.repositories.mappers

import data.dto.TaskDto
import logic.model.State
import logic.model.Task

fun Task.toTaskDto(): TaskDto{
	return TaskDto(
		id = this.id,
		title = this.title,
		description = this.description,
		stateId = this.state.id,
		projectId = this.projectId
	)
}

fun TaskDto.toTask(TaskState: State): Task{
	return Task(
		id = this.id,
		title = this.title,
		description = this.description,
		projectId = this.projectId,
		state = TaskState
	)
}