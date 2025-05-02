package ui

import di.appModule
import di.repositoryModule
import di.useCasesModule
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ui.utils.CLIMenu


fun main() {


    startKoin {
        modules(listOf(appModule, repositoryModule, useCasesModule))
    }

    val cliMenu :CLIMenu=getKoin().get()
    cliMenu.start()
}