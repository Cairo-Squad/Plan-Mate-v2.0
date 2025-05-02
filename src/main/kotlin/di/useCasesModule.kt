package di

import logic.usecase.Log.GetTaskLogsUseCase
import logic.usecase.project.*
import logic.usecase.task.*
import logic.usecase.user.CreateUserUseCase
import logic.usecase.user.EditUserUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import logic.usecase.user.LoginUserUseCase
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

    // single { GetAllProjectsUseCase(projectsRepository = get()) }
    single { GetTaskLogsUseCase(get()) }

    //user
    single { CreateUserUseCase(get()) }
    single { EditUserUseCase(get()) }
    single { LoginUserUseCase(get()) }



}