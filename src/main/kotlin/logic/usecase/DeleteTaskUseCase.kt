package logic.usecase

import logic.model.Task
import logic.repositories.TasksRepository

class DeleteTaskUseCase(
    private val tasksRepository: TasksRepository
) {
    fun execute(task: Task) {
        try {
            tasksRepository.deleteTask(task)
        } catch (exception: Exception) {
            throw Exception("An error happened! Please try again.")
        }
    }
}