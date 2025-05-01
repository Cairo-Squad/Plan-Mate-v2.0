package logic.usecase

import logic.model.Task
import logic.repositories.TasksRepository

class CreateTaskUseCase(private val repository: TasksRepository) {
    fun createTask(task: Task): Result<Unit> {
        return if (isValidTask(task)) {
            try {
                repository.createTask(task)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(IllegalArgumentException("Invalid task data"))
        }
    }

    private fun isValidTask(task: Task): Boolean {
        return task.title.isNotBlank() && task.projectId != null
    }
}

