package data.repositories

import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import data.repositories.mappers.toTaskDto
import logic.model.Task
import logic.repositories.TasksRepository
import java.util.*

class TasksRepositoryImpl(
    private val dataSource: DataSource
) : TasksRepository, BaseRepository() {

    override fun getTaskById(taskId: UUID): Task {
        return wrap {
            val taskDto = dataSource.getTaskById(taskId)
            val taskState = dataSource.getStateById(taskDto.stateId)
            taskDto.toTask(taskState.toState())
        }
    }

    override fun createTask(task: Task) {
        return wrap { dataSource.createTask(task.toTaskDto()) }
    }

    override fun editTask(task: Task) {
        wrap { dataSource.editTask(task.toTaskDto()) }
    }

    override fun getAllTasksByProjectId(projectId: UUID): List<Task> {
        return wrap {
            dataSource.getTasksByProjectId(projectId).map { taskDto ->
                val taskState = dataSource.getStateById(taskDto.stateId)
                taskDto.toTask(taskState.toState())
            }
        }
    }

    override fun deleteTask(task: Task) {
        wrap { dataSource.deleteTask(task.toTaskDto()) }
    }
}