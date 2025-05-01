package logic.repositories

import logic.model.Task
import java.util.UUID

interface TasksRepository {
    fun getTaskById(taskId: UUID): Task
    fun deleteTask(task: Task)
    fun createTask(task: Task): Result<Unit>
}