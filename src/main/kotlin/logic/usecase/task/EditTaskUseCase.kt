package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository

class EditTaskUseCase(
    private val tasksRepository: TasksRepository
) {
    suspend fun editTask(newTask : Task) {
        tasksRepository.editTask(task = newTask)
    }
}
