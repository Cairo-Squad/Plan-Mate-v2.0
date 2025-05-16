package di

import data.hashing.MD5PasswordEncryptor
import data.hashing.PasswordEncryptor
import data.repositories.*
import logic.repositories.*
import org.koin.dsl.module

val repositoryModule = module {
    single<ProjectsRepository> { ProjectsRepositoryImpl(get()) }
    single<TasksRepository> { TasksRepositoryImpl(get()) }
    single<StatesRepository> { StatesRepositoryImpl(get()) }
    single<TaskLogsRepository> { TaskLogsRepositoryImpl(get()) }
    single<ProjectLogsRepository> { ProjectLogsRepositoryImpl(get()) }

    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get()) }

    single<PasswordEncryptor> { MD5PasswordEncryptor() }
}