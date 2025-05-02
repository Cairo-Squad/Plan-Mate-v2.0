package logic.repositories

import logic.model.Task
import java.util.UUID

interface TasksRepository {
    fun getTaskById(taskId: UUID): Task
    fun editTask(task: Task)
    fun getAllTasksByProjectId(projectId: UUID): List<Task>
    fun deleteTask(task: Task)
    fun createTask(task: Task): Result<Unit>
}