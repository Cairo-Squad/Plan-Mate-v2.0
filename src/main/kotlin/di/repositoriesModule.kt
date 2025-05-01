package di

import data.repositories.ProjectsRepositoryImpl
import logic.repositories.ProjectsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProjectsRepository> { ProjectsRepositoryImpl(get()) }
    single<ProjectsRepository> { ProjectsRepositoryImpl(dataSource = get()) }
}