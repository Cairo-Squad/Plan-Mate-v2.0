package logic.usecase.task

import logic.exception.EmptyTitleException
import data.dto.EntityType
import data.dto.UserAction
import logic.model.Log
import logic.model.Task
import logic.model.User
import logic.repositories.TasksRepository
import ui.features.auth.UserSession.getUser
import java.time.LocalDateTime
import logic.usecase.Log.AddLogUseCase
import java.util.UUID

class CreateTaskUseCase(
    private val repository: TasksRepository, private val addLogUseCase: AddLogUseCase
) {
    suspend fun createTask(task: Task, user: User) {
        validateTask(task)
        repository.createTask(task)
        val log = Log(
            id = UUID.randomUUID(),
            entityId = task.id,
            entityTitle = task.title,
            entityType = EntityType.TASK,
            dateTime = LocalDateTime.now(),
            userId = user.id,
            userAction = UserAction.CreateTask(task.title, task.id, task.projectId)
        )


        addLogUseCase.addLog(log)
    }

    private fun validateTask(task: Task) {
        if (task.title.isBlank()) throw EmptyTitleException()
    }
}

