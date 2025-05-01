package di

import logic.usecase.CreateProjectUseCase
import logic.usecase.EditProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.project.GetProjectByIdUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single { EditProjectUseCase(get()) }
    single { CreateProjectUseCase(repository = get()) }
    single { GetProjectByIdUseCase(projectsRepository = get()) }
    single { GetAllProjectsUseCase(projectsRepository = get()) }
}