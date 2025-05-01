package di

import logic.usecase.EditProjectUseCase
import org.koin.dsl.module
import logic.usecase.CreateProjectUseCase
import logic.usecase.project.GetProjectByIdUseCase

val useCasesModule = module {
    single { EditProjectUseCase(get()) }
    single { CreateProjectUseCase(repository = get()) }
    single { GetProjectByIdUseCase(projectsRepository = get()) }
    single { GetAllProjectsUseCase(projectsRepository = get()) }
}