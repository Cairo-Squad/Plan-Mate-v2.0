package data.repositories

import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import data.repositories.mappers.toTaskDto
import logic.model.Task
import logic.repositories.TasksRepository
import java.util.UUID

class TasksRepositoryImpl(
    private val dataSource: DataSource
) : TasksRepository {

    override suspend fun getTaskById(taskId: UUID): Task {
        val taskDto = dataSource.getTaskById(taskId)
        val taskState = dataSource.getStateById(taskDto.stateId)
        return taskDto.toTask(taskState.toState())
    }

    override suspend fun createTask(task: Task) {
        return dataSource.createTask(task.toTaskDto())
    }

    override suspend fun editTask(task: Task) {
        dataSource.editTask(task.toTaskDto())
    }

    override suspend fun getAllTasksByProjectId(projectId: UUID): List<Task> {
        return dataSource.getTasksByProjectId(projectId).map { taskDto ->
            val taskState = dataSource.getStateById(taskDto.stateId)
            taskDto.toTask(taskState.toState())
        }
    }

    override suspend fun deleteTask(task: Task) {
        dataSource.deleteTask(task.toTaskDto())
    }
}