package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository

class DeleteTaskUseCase(
    private val tasksRepository: TasksRepository
) {
    suspend fun deleteTask(task: Task) {
        return tasksRepository.deleteTask(task)
    }
}