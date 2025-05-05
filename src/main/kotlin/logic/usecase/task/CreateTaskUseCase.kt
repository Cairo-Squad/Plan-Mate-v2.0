package logic.usecase.task

import logic.exception.EmptyTitleException
import logic.model.Task
import logic.repositories.TasksRepository

class CreateTaskUseCase(private val repository: TasksRepository) {
    fun createTask(task: Task) {
        validateTask(task)
        repository.createTask(task)
    }

    private fun validateTask(task: Task) {
        if (task.title.isBlank()) throw (EmptyTitleException())
    }
}

