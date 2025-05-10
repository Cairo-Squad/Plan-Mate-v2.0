package ui

import di.*
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ui.utils.CLIMenu

fun main() {

    startKoin {
        modules(listOf(uiModule, repositoryModule, useCasesModule, remoteDataSourceImplModule, mongoDatabaseModule))
    }

    val cliMenu: CLIMenu = getKoin().get()
    cliMenu.start()
}
