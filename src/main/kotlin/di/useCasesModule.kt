package di

import logic.usecase.project.AddProjectLogUseCase
import org.koin.dsl.module
import logic.usecase.CreateProjectUseCase
import logic.usecase.DeleteProjectUseCase
import logic.usecase.EditProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.project.GetProjectByIdUseCase

val useCasesModule = module {
    single { EditProjectUseCase(get()) }
    single { AddProjectLogUseCase(get()) }
    single { CreateProjectUseCase(repository = get()) }
    single { GetProjectByIdUseCase(projectsRepository = get()) }
    single { DeleteProjectUseCase(projectsRepository = get()) }
    single { GetAllProjectsUseCase(projectsRepository = get()) }
}