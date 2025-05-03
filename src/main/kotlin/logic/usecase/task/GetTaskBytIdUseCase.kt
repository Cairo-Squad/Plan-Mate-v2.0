package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository
import java.util.UUID

class GetTaskBytIdUseCase(private val tasksRepository: TasksRepository) {
    fun getTaskById(taskID: UUID): Task {
        return tasksRepository.getTaskById(taskID)
            ?: throw NoSuchElementException("Task with ID $taskID not found.")
    }

}