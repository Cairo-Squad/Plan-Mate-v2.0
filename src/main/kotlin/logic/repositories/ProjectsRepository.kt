package logic.repositories

import logic.model.Project

interface ProjectsRepository {
    fun getAllProjects(): Result<List<Project>>
}