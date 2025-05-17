package logic.usecase.task

import logic.model.*
import logic.repositories.TasksRepository
import logic.usecase.log.AddTaskLogUseCase
import logic.usecase.user.GetCurrentUserUseCase
import java.time.LocalDateTime

class DeleteTaskUseCase(
    private val tasksRepository: TasksRepository,
    private val addTaskLogUseCase: AddTaskLogUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,

    ) {
    suspend fun deleteTask(task: Task) {
        tasksRepository.deleteTask(task)

        val log = Log(
            entityId = task.id!!,
            entityTitle = task.title ?: "",
            entityType = EntityType.TASK,
            userId = getCurrentUserUseCase.getCurrentUser()?.id,
            dateTime = LocalDateTime.now(),
            userAction = ActionType.DELETE_TASK
        )

        addTaskLogUseCase.addTaskLog(log)
    }
}