package di

import logic.usecase.project.*
import org.koin.dsl.module

val useCasesModule = module {
    single { CreateProjectUseCase(projectRepository = get()) }
    single { EditProjectUseCase(get()) }
    single { DeleteProjectUseCase(projectsRepository = get()) }
    single { GetProjectByIdUseCase(projectsRepository = get()) }
    single { GetAllProjectsUseCase(projectsRepository = get()) }
    single { AddProjectLogUseCase(get()) }
}