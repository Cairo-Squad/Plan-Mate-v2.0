package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository

class GetAllTasksUseCase(val tasksRepository: TasksRepository) {
	suspend fun getAllTasks(): List<Task>{
		return tasksRepository.getAllTasks()
	}
}