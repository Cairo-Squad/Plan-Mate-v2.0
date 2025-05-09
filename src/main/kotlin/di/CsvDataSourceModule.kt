package di

import data.dataSource.localDataSource.file.*
import data.dataSource.util.CsvConstants
import data.dataSource.localDataSource.file.LocalDataSource
import data.dto.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val localDataSourceModule = module {
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
            filePath = CsvConstants.USERS_CSV_FILE_PATH,
            headers = CsvConstants.USERS_CSV_FILE_HEADERS
        )
    }

    single<FileHandler<LogDto>>(named("logHandler")) {
        LogsCsvHandler(
            filePath = CsvConstants.LOGS_CSV_FILE_PATH,
            headers = CsvConstants.LOGS_CSV_FILE_HEADERS
        )
    }

    single<LocalDataSource> {
        LocalDataSourceImpl(
            logsCsvHandler = get(named("logHandler")),
            projectsCsvHandler = get(named("projectsHandler")),
            statesCsvHandler = get(named("statesHandler")),
            tasksCsvHandler = get(named("tasksHandler")),
            usersCsvHandler = get(named("userHandler")),
        )
    }
}