package logic.usecase.task

import logic.model.EntityType
import logic.model.Log
import logic.model.Task
import logic.model.UserAction
import logic.repositories.TasksRepository
import logic.usecase.log.AddTaskLogUseCase
import logic.usecase.user.GetCurrentUserUseCase
import java.time.LocalDateTime
import java.util.UUID

class CreateTaskUseCase(
    private val tasksRepository: TasksRepository,
    private val addTaskLogUseCase: AddTaskLogUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val validationTask: ValidationTask
) {
    suspend fun createTask(task: Task): UUID {
        validationTask.validateCreateTask(task)

        val taskId = tasksRepository.createTask(task)

        val log = Log(
            entityId = taskId,
            entityTitle = task.title ?: "",
            entityType = EntityType.TASK,
            userId = getCurrentUserUseCase.getCurrentUser()?.id,
            dateTime = LocalDateTime.now(),
            userAction = UserAction.CreateTask(taskId, "Created task")
        )

        addTaskLogUseCase.addTaskLog(log)

        return taskId
    }
}