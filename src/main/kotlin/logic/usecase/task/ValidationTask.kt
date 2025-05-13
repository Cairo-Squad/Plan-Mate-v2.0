package logic.usecase.task

import data.customException.PlanMateException
import logic.model.Task

class ValidationTask {
    fun validateCreateTask(task: Task) {
        if (task.title.isNullOrBlank()) throw PlanMateException.ValidationException.TitleException()
    }
}