package data.repositories

import data.dataSource.DataSource
import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import data.repositories.mappers.toTaskDto
import logic.model.Task
import logic.repositories.TasksRepository
import java.util.UUID

class TasksRepositoryImpl(
    private val dataSource: DataSource
) : TasksRepository {

    override fun getTaskById(taskID: UUID): Task {

        val taskDto = dataSource.getTaskById(taskID)
        val taskState = dataSource.getStateById(taskDto.stateId)
        return taskDto.toTask(taskState.toState())
    }

    override fun createTask(task: Task): Result<Unit> {
        return dataSource.createTask(task.toTaskDto())
    }

    override fun editTask(task: Task) {
        dataSource.editTask(task.toTaskDto())
    }

    override fun getAllTasksByProjectId(projectId: UUID): List<Task> {
        return dataSource.getTasksByProjectId(projectId).map { taskDto ->
            val taskState = dataSource.getStateById(taskDto.stateId)
            taskDto.toTask(taskState.toState())
        }
    }

    override fun deleteTask(task: Task) {
        dataSource.deleteTask(task.toTaskDto())
    }


}