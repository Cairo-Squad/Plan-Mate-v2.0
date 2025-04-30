package di

import logic.usecase.EditProjectUseCase
import org.koin.dsl.module

val useCase = module {
    single { EditProjectUseCase(get()) }
}
