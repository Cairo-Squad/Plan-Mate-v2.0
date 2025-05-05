package di

import com.mongodb.client.MongoDatabase
import data.dataSource.MongoDataSource
import data.database.mongo.*
import data.dto.*
import data.repositories.DataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mongoDataSourceModule = module {
    // Provide MongoDB database instance
    single<MongoDatabase> {
        MongoDBConnection.getDatabase()
    }

    // Define handlers for each entity
    single<MongoDBHandler<ProjectDto>>(named("projectsHandler")) {
        ProjectsMongoHandlerImpl(
            database = get()
        )
    }

    single<MongoDBHandler<LogDto>>(named("logsHandler")) {
        LogsMongoHandlerImpl(
            database = get()
        )
    }

    single<MongoDBHandler<StateDto>>(named("statesHandler")) {
        StatesMongoHandlerImpl(
            database = get()
        )
    }

    single<MongoDBHandler<TaskDto>>(named("tasksHandler")) {
        TasksMongoHandlerImpl(
            database = get()
        )
    }

    single<MongoDBHandler<UserDto>>(named("usersHandler")) {
        UsersMongoHandlerImpl(
            database = get()
        )
    }

    // DataSource implementation
    single<DataSource> {
        MongoDataSource(
            database = get()
        )
    }
}