package logic.repositories

import logic.model.Task

interface TasksRepository {
    fun createTask(task: Task): Result<Unit>
    fun deleteTask(task: Task)
}