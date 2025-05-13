package logic.repositories

import logic.model.Task
import java.util.UUID

interface TasksRepository {
    suspend fun getTaskById(taskId: UUID): Task
    suspend fun editTask(task: Task)
    suspend fun getAllTasksByProjectId(projectId: UUID): List<Task>
    suspend fun deleteTask(task: Task)
    suspend fun createTask(task: Task):Boolean
    suspend fun getAllTasks():List<Task>
}