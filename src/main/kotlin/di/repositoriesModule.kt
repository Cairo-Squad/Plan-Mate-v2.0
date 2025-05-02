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
    single<LogsRepository> { LogsRepositoryImpl(get()) }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }

    single<PasswordEncryptor> { MD5PasswordEncryptor() }
}