package di

import logic.usecase.project.GetAllProjectsUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single { GetAllProjectsUseCase(projectsRepository = get()) }
}