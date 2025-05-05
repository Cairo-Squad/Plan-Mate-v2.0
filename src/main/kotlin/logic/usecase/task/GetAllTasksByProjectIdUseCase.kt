package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository
import java.util.UUID

class GetAllTasksByProjectIdUseCase(private val tasksRepository: TasksRepository) {
    fun execute(projectId: UUID): List<Task> {
        return tasksRepository.getAllTasksByProjectId(projectId)
    }
}
