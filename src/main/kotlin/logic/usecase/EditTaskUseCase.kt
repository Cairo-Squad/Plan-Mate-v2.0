package logic.usecase

import logic.model.Task
import logic.repositories.TasksRepository

class EditTaskUseCase(
    private val repository: TasksRepository
) {
    operator fun invoke(newTask: Task, oldTask: Task) {

    }
}