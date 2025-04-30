package logic.repositories

import logic.model.Project

interface ProjectsRepository {

    fun editProject(newProject: Project)
}