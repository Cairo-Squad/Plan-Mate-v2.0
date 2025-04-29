package logic.repositories

import data.dto.ProjectDto

interface ProjectsRepository {
    fun getAllProjects(): List<ProjectDto>

}