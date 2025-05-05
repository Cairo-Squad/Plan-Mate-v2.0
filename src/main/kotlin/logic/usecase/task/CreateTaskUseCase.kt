package logic.usecase.task

import data.dto.EntityType
import data.dto.UserAction
import logic.model.Log
import logic.model.Task
import logic.repositories.TasksRepository
import logic.usecase.Log.AddLogUseCase
import ui.features.auth.UserSession.getUser
import java.time.LocalDateTime
import java.util.UUID
class CreateTaskUseCase(
    private val repository: TasksRepository,
    private val addLogUseCase: AddLogUseCase
) {
    fun createTask(task: Task): Result<Unit> {
        return if (isValidTask(task)) {
            try {
                repository.createTask(task)


                val log = Log(
                    id = UUID.randomUUID(),
                    entityId = task.id,
                    entityTitle = task.title,
                    entityType = EntityType.TASK,
                    dateTime = LocalDateTime.now(),
                    userId = getUser()!!.id,
                    userAction = UserAction.CreateTask(task.title, task.id, task.projectId)
                )


                addLogUseCase.recordLog(log)

                Result.success(Unit)
            } catch (e: IllegalArgumentException) {
                println("Invalid task data: ${e.message}")
                Result.failure(e)
            } catch (e: Exception) {
                println("Error creating task: ${e.message}")
                Result.failure(Exception("Task creation failed due to unexpected error"))
            }
        } else {
            Result.failure(IllegalArgumentException("Task validation failed: Invalid task data"))
        }
    }

    private fun isValidTask(task: Task): Boolean {
        return task.title.isNotBlank() && task.projectId != null
    }
}
