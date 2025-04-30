package di

import data.repositories.ProjectsRepositoryImpl
import logic.repositories.ProjectsRepository
import org.koin.dsl.module

val repositories = module {
    single<ProjectsRepository> { ProjectsRepositoryImpl(get()) }
}