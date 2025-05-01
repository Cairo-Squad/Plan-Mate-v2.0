package di

import logic.usecase.DeleteProjectUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { DeleteProjectUseCase(projectsRepository = get()) }
}