package logic.usecase

import logic.model.Task
import logic.repositories.TasksRepository
import java.util.UUID

class GetTaskBytIdUseCase(private val repository: TasksRepository) {
    fun getTaskById(taskID: UUID): Task {
         TODO()
    }

}