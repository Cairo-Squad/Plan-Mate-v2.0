package di

import com.mongodb.client.MongoDatabase
import data.dataSource.remoteDataSource.mongo.*
import data.dto.*
import data.dataSource.localDataSource.file.LocalDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val remoteDataSourceImplModule = module {
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
    single<RemoteDataSource> {
        RemoteDataSourceImpl(
            database = get()
        )
    }
}