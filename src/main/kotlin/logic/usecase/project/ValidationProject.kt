package logic.usecase.project

import data.dto.UserType
import logic.exception.NameException
import logic.exception.TitleException
import logic.exception.InvalidUserTypeException
import logic.model.Project
import logic.model.User

class ValidationProject {
	fun validateCreateProject(project: Project, user: User) {
		if (user.type != UserType.ADMIN) throw InvalidUserTypeException()
		if (project.title?.isBlank() == true) throw TitleException()
	}
	
	fun validateEditProject(project: Project){
		if (project.title?.isBlank() == true) {
			throw NameException()
		}
	}
}