package di

import data.dataSource.CsvDataSource
import data.dataSource.DataSource
import data.database.FileHandler
import data.database.ProjectsCsvHandler
import data.dto.ProjectDto
import org.koin.dsl.module

val dataModule = module {
    single<FileHandler<ProjectDto>> { ProjectsCsvHandler(TODO(), TODO()) }
    single<DataSource> { CsvDataSource(projectsCsvHandler = get()) }
}