package di

import logic.usecase.Log.*
import logic.usecase.project.*
import logic.usecase.state.*
import logic.usecase.task.*
import logic.usecase.user.*
import org.koin.dsl.module

val useCasesModule = module {
    //project
    single { CreateProjectUseCase(projectRepository = get()) }
    single { EditProjectUseCase(get()) }
    single { DeleteProjectUseCase(projectsRepository = get()) }
    single { GetProjectByIdUseCase(projectsRepository = get()) }
    single { GetAllProjectsUseCase(projectsRepository = get()) }
    single { AddProjectLogUseCase(get()) }

    //task
    single { CreateTaskUseCase(get()) }
    single { EditTaskUseCase(get()) }
    single { DeleteTaskUseCase(get()) }
    single { GetTaskBytIdUseCase(get()) }
    single { GetAllTasksByProjectIdUseCase(get()) }

    // Logs
    single { GetTaskLogsUseCase(get()) }
    single { GetProjectLogUseCase(get()) }

    //user
    single { CreateUserUseCase(get()) }
    single { EditUserUseCase(get()) }
    single { LoginUserUseCase(get()) }
    single { DeleteUserUseCase(get()) }
    single { GetAllUsersUseCase(get()) }

    // States
    single { GetAllStatesUseCase(get()) }
    single { CreateStateUseCase(get()) }
}