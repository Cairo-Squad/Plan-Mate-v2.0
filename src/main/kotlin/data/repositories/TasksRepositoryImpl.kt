package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import data.repositories.mappers.toTaskDto
import logic.model.Task
import logic.repositories.TasksRepository
import java.util.*

class TasksRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : TasksRepository, BaseRepository() {

    override suspend fun getTaskById(taskId: UUID): Task {
        return wrap {
            val taskDto = remoteDataSource.getTaskById(taskId)
            val taskState = remoteDataSource.getStateById(taskDto.stateId)
            taskDto.toTask(taskState.toState())
        }
    }

    override suspend fun createTask(task: Task) {
        return wrap { remoteDataSource.createTask(task.toTaskDto()) }
    }

    override suspend fun editTask(task: Task) {
        wrap { remoteDataSource.editTask(task.toTaskDto()) }
    }

    override suspend fun getAllTasksByProjectId(projectId: UUID): List<Task> {
        return wrap {
            remoteDataSource.getTasksByProjectId(projectId).map { taskDto ->
                val taskState = remoteDataSource.getStateById(taskDto.stateId)
                taskDto.toTask(taskState.toState())
            }
        }
    }

    override suspend fun deleteTask(task: Task) {
        wrap { remoteDataSource.deleteTask(task.toTaskDto()) }
    }
}