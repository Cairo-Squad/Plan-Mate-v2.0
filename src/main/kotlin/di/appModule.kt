package di

import data.dataSource.CsvDataSource
import data.dataSource.DataSource
import data.database.FileHandler
import data.database.ProjectsCsvHandler
import data.database.StatesCsvHandler
import data.database.TasksCsvHandler
import data.dto.ProjectDto
import data.dto.StateDto
import data.dto.TaskDto
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<FileHandler<ProjectDto>>(named("projectsHandler")) {
        ProjectsCsvHandler(
            filePath = "project.csv",
            headers = listOf("id", "title", "description", "createdBy", "tasks", "stateId")
        )
    }

    single<FileHandler<StateDto>>(named("statesHandler")) {
        StatesCsvHandler(
            filePath = "state.csv",
            headers = listOf("id", "title")
        )
    }

    single<FileHandler<TaskDto>>(named("tasksHandler")) {
        TasksCsvHandler(
            filePath = "task.csv",
            headers = listOf("id", "title", "description", "stateId", "projectId")
        )
    }
}