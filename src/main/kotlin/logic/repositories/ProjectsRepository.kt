package logic.repositories

import data.dto.ProjectDto

interface ProjectsRepository {
    fun getProject(): List<ProjectDto>

}