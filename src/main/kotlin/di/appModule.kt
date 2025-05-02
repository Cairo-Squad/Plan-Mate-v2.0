package di

import data.dataSource.CsvDataSource
import data.dataSource.DataSource
import data.database.*
import data.database.util.CsvConstants
import data.dto.ProjectDto
import data.dto.StateDto
import data.dto.TaskDto
import data.dto.UserDto
import logic.model.User
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<FileHandler<ProjectDto>>(named("projectsHandler")) {
        ProjectsCsvHandler(
            filePath = CsvConstants.PROJECTS_CSV_FILE_PATH,
            headers = CsvConstants.PROJECTS_CSV_FILE_HEADERS
        )
    }
    single<FileHandler<TaskDto>>(named("tasksHandler")) {
        TasksCsvHandler(
            filePath = CsvConstants.TASKS_CSV_FILE_PATH,
            headers = CsvConstants.TASKS_CSV_FILE_HEADERS
        )
    }
    single<FileHandler<StateDto>>(named("statesHandler")) {
        StatesCsvHandler(
            filePath = CsvConstants.STATES_CSV_FILE_PATH,
            headers = CsvConstants.STATES_CSV_FILE_HEADERS
        )
    }
    single<FileHandler<UserDto>>(named("userHandler")) {
        UsersCsvHandler(
            filePath = CsvConstants.PROJECTS_CSV_FILE_PATH,
            headers = CsvConstants.PROJECTS_CSV_FILE_HEADERS
        )
    }

    single<DataSource> { CsvDataSource(get(), get(), get(), get(), get()) }
}