package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository
import java.util.UUID

class CreateTaskUseCase(
    private val tasksRepository: TasksRepository,
    private val validationTask : ValidationTask
) {
    suspend fun createTask(task: Task): UUID {
        validationTask.validateCreateTask(task)
        return tasksRepository.createTask(task)
    }
}