package di

import logic.usecase.CreateProjectUseCase
import org.koin.dsl.module

val useCasesModules = module {
    single { CreateProjectUseCase(repository = get()) }
}