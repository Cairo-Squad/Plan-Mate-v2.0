package di

import logic.usecase.project.GetProjectByIdUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single { GetProjectByIdUseCase(projectsRepository = get()) }
}