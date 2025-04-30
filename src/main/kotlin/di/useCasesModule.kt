package di

import logic.usecase.EditProjectDescriptionUseCase
import logic.usecase.EditProjectTitleUseCase
import org.koin.dsl.module

val useCase = module {
    single { EditProjectTitleUseCase(get()) }
    single { EditProjectDescriptionUseCase(get()) }
}
