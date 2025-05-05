package logic.usecase.task

import logic.model.Task
import logic.repositories.TasksRepository

class DeleteTaskUseCase(
    private val tasksRepository: TasksRepository
) {
    fun execute(task: Task) {
        return tasksRepository.deleteTask(task)
    }
}