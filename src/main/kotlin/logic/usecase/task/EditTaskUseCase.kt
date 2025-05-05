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

class EditTaskUseCase(
    private val repository: TasksRepository,
    private val addLogUseCase: AddLogUseCase
) {
    operator fun invoke(newTask: Task, oldTask: Task): Result<Unit> {
        repository.editTask(newTask)

        val userId = getUser()!!.id

        val changes = listOf(
            "title" to (oldTask.title to newTask.title),
            "status" to (oldTask.state.title to newTask.state.title)
        )

        changes.filter { it.second.first != it.second.second }.forEach { (field, values) ->
            val log = Log(
                id = UUID.randomUUID(),
                entityId = newTask.id,
                entityTitle = newTask.title,
                entityType = EntityType.TASK,
                dateTime = LocalDateTime.now(),
                userId = userId,
                userAction = UserAction.EditTask(
                    newTask.id,
                    "$field changed from '${values.first}' to '${values.second}'"
                )
            )
            addLogUseCase.recordLog(log)
        }

        return Result.success(Unit)
    }

}
