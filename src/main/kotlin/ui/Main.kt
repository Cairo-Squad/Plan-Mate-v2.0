package ui

import data.dataSource.CsvDataSource
import data.dataSource.DataSource
import data.database.ProjectsCsvHandler
import data.repositories.ProjectsRepositoryImpl
import di.repositoryModule
import logic.repositories.ProjectsRepository
import logic.usecase.project.GetProjectByIdUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import java.util.UUID

fun main() {

    startKoin {
        modules(repositoryModule)
    }
    val sourcs: DataSource = getKoin().get()
//    val result = sourcs.getAllStates()
    val repo: ProjectsRepository = ProjectsRepositoryImpl(sourcs)
    val usecase = GetProjectByIdUseCase(repo)

    println(
        usecase.getProjectById(UUID.fromString("b6d0e5e1-2a64-4b2a-b8b3-dcb663b876e7"))
            .map { it.toString() }
    )
//    result?.let { print(it) }
}