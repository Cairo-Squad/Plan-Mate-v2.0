package logic.usecase.task

import logic.exception.TitleException
import logic.model.Task

class ValidationTask {
	fun validateCreateTask(task:Task){
		if (task.title?.isBlank() == true) throw TitleException()
	}
}