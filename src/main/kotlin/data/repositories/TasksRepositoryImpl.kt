package data.repositories

import data.dataSource.DataSource
import data.database.ProjectsCsvHandler
import data.database.TasksCsvHandler
import data.dto.TaskDto
import logic.model.Task
import logic.repositories.TasksRepository
import data.repositories.mappers.toTaskDto

class TasksRepositoryImpl(
    private val dataSource: DataSource
) : TasksRepository {

    override fun createTask(task: Task): Result<Unit> {
        return dataSource.createTask(task.toTaskDto())
    }

}