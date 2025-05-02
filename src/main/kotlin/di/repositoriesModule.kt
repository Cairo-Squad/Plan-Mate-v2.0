package di

import data.repositories.LogsRepositoryImpl
import data.repositories.ProjectsRepositoryImpl
import data.repositories.StatesRepositoryImpl
import data.repositories.TasksRepositoryImpl
import logic.repositories.LogsRepository
import logic.repositories.ProjectsRepository
import logic.repositories.StatesRepository
import logic.repositories.TasksRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProjectsRepository> { ProjectsRepositoryImpl(dataSource = get()) }
    single<TasksRepository> { TasksRepositoryImpl(dataSource = get()) }
    single<StatesRepository> {StatesRepositoryImpl(dataSource = get()) }
    single<LogsRepository> { LogsRepositoryImpl(dataSource = get()) }
    single<ProjectsRepository> {ProjectsRepositoryImpl(dataSource = get()) }

}