package logic.usecase.project

import data.dto.UserType
import logic.exception.EmptyNameException
import logic.exception.EmptyTitleException
import logic.exception.InvalidUserException
import logic.model.Project
import logic.model.User

class ValidationProject {
	fun validateCreateProject(project: Project, user: User) {
		if (user.type != UserType.ADMIN) throw InvalidUserException()
		if (project.title.isBlank()) throw EmptyTitleException()
	}
	
	fun validateEditProject(project: Project){
		if (project.title.isBlank()) {
			throw EmptyNameException()
		}
	}
}