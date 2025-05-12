package logic.usecase.task

import logic.exception.EmptyTitleException
import logic.model.Task

class ValidationTask {
	fun validateCreateTask(task:Task){
		if (task.title?.isBlank() == true) throw EmptyTitleException()
	}
}