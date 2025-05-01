package logic.repositories

import logic.model.Task
import java.util.UUID

interface TasksRepository {
   fun createTask(task: Task):Result<Unit>
    fun getTaskById(taskId: UUID): Task
}