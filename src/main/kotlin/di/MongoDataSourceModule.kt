package di

import data.dataSource.remoteDataSource.RemoteDataSource
import data.dataSource.remoteDataSource.mongo.*
import data.dto.*
import data.dataSource.remoteDataSource.mongo.handler.MongoDBHandler
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mongoRemoteDataSourceModule = module {

    single<MongoDBHandler<ProjectDto>>(named("projectsHandler")) {
        ProjectsMongoHandlerImpl(
            database = get()
        )
    }

    single<MongoDBHandler<LogDto>>(named("taskLogsHandler")) {
        LogsMongoHandlerImpl(
            database = get(),
            collectionName = "taskLogs"
        )
    }

    single<MongoDBHandler<LogDto>>(named("projectLogsHandler")) {
        LogsMongoHandlerImpl(
            database = get(),
            collectionName = "projectLogs"
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

    single<RemoteDataSource> {
        RemoteDataSourceImpl(
            taskLogsHandler = get(named("taskLogsHandler")),
            projectLogsHandler = get(named("projectLogsHandler")),
            projectsHandler = get(named("projectsHandler")),
            statesHandler = get(named("statesHandler")),
            tasksHandler = get(named("tasksHandler")),
            usersHandler = get(named("usersHandler")),
            passwordEncryptor = get()
        )
    }
}