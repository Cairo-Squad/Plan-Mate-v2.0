package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository

class EditTaskUseCase(
    private val repository: TasksRepository
) {
    fun editTask(newTask: Task, oldTask: Task) {
        validateUserInputs(
            newTask = newTask,
            oldTask = oldTask
        )
        repository.editTask(task = newTask)
    }

    private fun validateUserInputs(newTask: Task, oldTask: Task) {
        if (newTask == oldTask)
            throw IllegalStateException("task is not changed")

        if (newTask.title.isBlank()) {
            throw IllegalArgumentException("Title must not be empty")
        }

        if (newTask.description.isBlank()) {
            throw IllegalArgumentException("Description must not be empty")
        }
    }
}