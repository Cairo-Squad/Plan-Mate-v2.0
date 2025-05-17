package logic.usecase.task

import logic.model.EntityType
import logic.model.Log
import logic.model.Task
import logic.model.UserAction
import logic.repositories.TasksRepository
import logic.usecase.log.AddTaskLogUseCase
import logic.usecase.user.GetCurrentUserUseCase
import java.time.LocalDateTime

class EditTaskUseCase(
    private val tasksRepository: TasksRepository,
    private val addTaskLogUseCase: AddTaskLogUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    ) {
    suspend fun editTask(newTask: Task) {
        tasksRepository.editTask(task = newTask)

        val log = Log(
            entityId = newTask.id!!,
            entityTitle = newTask.title ?: "",
            entityType = EntityType.TASK,
            userId = getCurrentUserUseCase.getCurrentUser()?.id,
            dateTime = LocalDateTime.now(),
            userAction = UserAction.EditTask(newTask.id, "Edited task")
        )

        addTaskLogUseCase.addTaskLog(log)
    }
}
