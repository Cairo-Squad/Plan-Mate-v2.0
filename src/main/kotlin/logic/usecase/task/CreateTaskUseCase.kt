package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository

class CreateTaskUseCase(
    private val repository: TasksRepository,
    private val validationTask : ValidationTask
) {
    suspend fun createTask(task: Task):Task {
        validationTask.validateCreateTask(task)
        return repository.createTask(task)
    }
}