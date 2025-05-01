package logic.repositories

import data.dto.ProjectDto
import logic.model.Project

interface ProjectsRepository {
    fun getAllProjects(): List<ProjectDto>
}