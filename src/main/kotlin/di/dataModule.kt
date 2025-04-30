package di

import data.dataSource.CsvDataSource
import data.dataSource.DataSource
import data.database.FileHandler
import data.database.ProjectsCsvHandler
import data.database.util.CsvConstants.PROJECTS_CSV_FILE_HEADERS
import data.database.util.CsvConstants.PROJECTS_CSV_FILE_PATH
import data.dto.ProjectDto
import org.koin.dsl.module

val dataModule = module {
    single<FileHandler<ProjectDto>> { ProjectsCsvHandler(PROJECTS_CSV_FILE_PATH, PROJECTS_CSV_FILE_HEADERS) }
    single<DataSource> { CsvDataSource(projectsCsvHandler = get()) }
}