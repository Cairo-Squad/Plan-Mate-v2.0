package data.repositories

import data.dataSource.DataSource
import logic.repositories.TasksRepository

class TasksRepositoryImpl (
    private val dataSource: DataSource
): TasksRepository {

}