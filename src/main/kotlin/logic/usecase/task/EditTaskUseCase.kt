package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository
import logic.usecase.log.AddLogUseCase

class EditTaskUseCase(
    private val repository: TasksRepository,
    private val addLogUseCase: AddLogUseCase
) {
    suspend fun editTask(newTask : Task, oldTask : Task) {
//        validateUserInputs(
//            newTask = newTask,
//            oldTask = oldTask
//        )
//        repository.editTask(task = newTask)
//        val userId = getUser()!!.id
//
//        val changes = listOf(
//            "title" to (oldTask.title to newTask.title),
//            "status" to (oldTask.state.title to newTask.state.title)
//        )
//
//        changes.filter { it.second.first != it.second.second }.forEach { (field, values) ->
//            val log = Log(
//                id = UUID.randomUUID(),
//                entityId = newTask.id,
//                entityTitle = newTask.title,
//                entityType = EntityType.TASK,
//                dateTime = LocalDateTime.now(),
//                userId = userId,
//                userAction = UserAction.EditTask(
//                    newTask.id,
//                    "$field changed from '${values.first}' to '${values.second}'"
//                )
//            )
//            addLogUseCase.addLog(log)
//        }
    }
}
