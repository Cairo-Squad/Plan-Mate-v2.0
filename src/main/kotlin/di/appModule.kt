package di

import data.database.FileHandler
import data.database.ProjectsCsvHandler
import data.database.StatesCsvHandler
import data.database.TasksCsvHandler
import data.database.util.CsvConstants
import data.dto.ProjectDto
import data.dto.StateDto
import data.dto.TaskDto
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<FileHandler<ProjectDto>>(named("projectsHandler")) {
        ProjectsCsvHandler(
            filePath = CsvConstants.PROJECTS_CSV_FILE_PATH,
            headers = CsvConstants.PROJECTS_CSV_FILE_HEADERS
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