package logic.usecase.task

import data.dto.EntityType
import data.dto.UserAction
import logic.model.Log
import logic.model.Task
import logic.repositories.TasksRepository
import logic.usecase.log.AddLogUseCase
import ui.features.auth.UserSession.getUser
import java.time.LocalDateTime
import java.util.*

class CreateTaskUseCase(
    private val repository: TasksRepository,
    private val addLogUseCase: AddLogUseCase
) {
    fun createTask(task: Task) {
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
        addLogUseCase.addLog(log)
    }
}

