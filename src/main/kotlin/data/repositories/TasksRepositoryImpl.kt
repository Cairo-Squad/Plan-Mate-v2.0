package data.repositories

import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import data.repositories.mappers.toTaskDto
import logic.model.Task
import logic.repositories.TasksRepository
import java.util.UUID

class TasksRepositoryImpl(
    private val csvDataSource: DataSource
) : TasksRepository {

    override fun getTaskById(taskId: UUID): Task {
        val taskDto = csvDataSource.getTaskById(taskId)
        val taskState = csvDataSource.getStateById(taskDto.stateId)
        return taskDto.toTask(taskState.toState())
    }

    override fun createTask(task: Task): Result<Unit> {
        return csvDataSource.createTask(task.toTaskDto())
    }

    override fun editTask(task: Task) {
        csvDataSource.editTask(task.toTaskDto())
    }

    override fun getAllTasksByProjectId(projectId: UUID): List<Task> {
        return csvDataSource.getTasksByProjectId(projectId).map { taskDto ->
            val taskState = csvDataSource.getStateById(taskDto.stateId)
            taskDto.toTask(taskState.toState())
        }
    }

    override fun deleteTask(task: Task) {
        csvDataSource.deleteTask(task.toTaskDto())
    }
}