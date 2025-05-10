package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository
import java.util.UUID

class GetTaskBytIdUseCase(private val tasksRepository: TasksRepository) {
    suspend fun getTaskById(taskID: UUID): Task {
        return tasksRepository.getTaskById(taskID)
    }
}