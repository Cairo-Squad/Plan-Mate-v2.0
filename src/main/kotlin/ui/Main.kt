package ui

import di.*
import logic.model.User
import logic.usecase.user.CreateUserUseCase
import data.dto.UserType
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import java.util.UUID
import ui.utils.CLIMenu

fun main() {

    startKoin {
        modules(listOf(uiModule, repositoryModule, useCasesModule, mongoDataSourceModule))
    }

    val cliMenu: CLIMenu = getKoin().get()
    cliMenu.start()
}
