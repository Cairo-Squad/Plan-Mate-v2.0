package di

import logic.usecase.CreateProjectUseCase
import org.koin.dsl.module
import logic.usecase.project.GetProjectByIdUseCase

val useCasesModule = module {
    single { CreateProjectUseCase(repository = get()) }
    single { GetProjectByIdUseCase(projectsRepository = get()) }
    single { GetAllProjectsUseCase(projectsRepository = get()) }
}