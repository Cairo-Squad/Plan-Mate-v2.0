package logic.usecase.task

import logic.model.EntityType
import logic.model.Log
import logic.model.Task
import logic.model.UserAction
import logic.repositories.TasksRepository
import logic.usecase.log.AddTaskLogUseCase
import java.time.LocalDateTime

class DeleteTaskUseCase(
    private val tasksRepository: TasksRepository,
    private val addTaskLogUseCase: AddTaskLogUseCase,

    ) {
    suspend fun deleteTask(task: Task) {
        tasksRepository.deleteTask(task)

        val log = Log(
            entityId = task.id!!,
            entityTitle = task.title ?: "",
            entityType = EntityType.TASK,
            dateTime = LocalDateTime.now(),
            userAction = UserAction.DeleteTask(task.id, "Deleted task")
        )

        addTaskLogUseCase.addTaskLog(log)
    }
}