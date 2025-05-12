package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository

class EditTaskUseCase(
    private val repository: TasksRepository
) {
    suspend fun editTask(newTask : Task) {
        repository.editTask(task = newTask)
    }
}
