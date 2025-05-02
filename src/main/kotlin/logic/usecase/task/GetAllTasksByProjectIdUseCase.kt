package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository
import java.util.UUID

class GetAllTasksByProjectIdUseCase(private val tasksRepository: TasksRepository) {
    fun execute(projectId: UUID): Result<List<Task>> {
        return try {
            val tasks = tasksRepository.getAllTasksByProjectId(projectId)
            Result.success(tasks)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
