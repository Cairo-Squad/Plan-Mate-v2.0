package ui

import di.*
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ui.utils.CLIMenu

fun main() {
    startKoin {
        modules(listOf(uiModule, repositoryModule, useCasesModule, dataSourceModule))
    }

    val cliMenu: CLIMenu = getKoin().get()
    cliMenu.start()
}