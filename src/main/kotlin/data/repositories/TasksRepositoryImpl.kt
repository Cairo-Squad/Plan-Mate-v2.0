package data.repositories

import data.dataSource.remoteDataSource.mongo.RemoteDataSource
import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import data.repositories.mappers.toTaskDto
import logic.model.Task
import logic.repositories.TasksRepository
import java.util.*

class TasksRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : TasksRepository {

    override suspend fun getTaskById(taskId: UUID): Task {
        val taskDto = remoteDataSource.getTaskById(taskId)
        val taskState = remoteDataSource.getStateById(taskDto.stateId)
        return taskDto.toTask(taskState.toState())
    }

    override suspend fun createTask(task: Task) {
        return remoteDataSource.createTask(task.toTaskDto())
    }

    override suspend fun editTask(task: Task) {
        remoteDataSource.editTask(task.toTaskDto())
    }

    override suspend fun getAllTasksByProjectId(projectId: UUID): List<Task> {
        return remoteDataSource.getTasksByProjectId(projectId).map { taskDto ->
            val taskState = remoteDataSource.getStateById(taskDto.stateId)
            taskDto.toTask(taskState.toState())
        }
    }

    override suspend fun deleteTask(task: Task) {
        remoteDataSource.deleteTask(task.toTaskDto())
    }
}