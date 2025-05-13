package logic.usecase.project

import logic.model.UserType
import data.customException.PlanMateException

import logic.model.Project
import logic.model.User

class ValidationProject {
	fun validateCreateProject(project: Project, user: User) {
		if (user.type != UserType.ADMIN) throw PlanMateException.ValidationException.InvalidUserTypeException()
		if (project.title?.isBlank() == true) throw PlanMateException.ValidationException.TitleException()
	}
	
	fun validateEditProject(project: Project){
		if (project.title?.isBlank() == true) {
			throw PlanMateException.ValidationException.NameException()
		}
	}
}