package logic.usecase.task

import logic.exception.EmptyTitleException
import logic.model.Task
import logic.repositories.TasksRepository
import logic.usecase.Log.AddLogUseCase

class CreateTaskUseCase(
    private val repository: TasksRepository, private val addLogUseCase: AddLogUseCase
) {
    suspend fun createTask(task: Task) {
        validateTask(task)
        repository.createTask(task)
      /*  val log = Log(
            id = UUID.randomUUID(),
            entityId = task.id,
            entityTitle = task.title,
            entityType = EntityType.TASK,
            dateTime = LocalDateTime.now(),
            userId = getUser()!!.id,
            userAction = UserAction.CreateTask(task.title, task.id, task.projectId)
        )


        addLogUseCase.addLog(log)*/
    }

    private fun validateTask(task: Task) {
        if (task.title.isBlank()) throw EmptyTitleException()
    }
}

