package di

import logic.usecase.Log.*
import logic.usecase.project.*
import logic.usecase.state.*
import logic.usecase.task.*
import logic.usecase.user.*
import org.koin.dsl.module

val useCasesModule = module {
    // region Projects
    single { CreateProjectUseCase(projectRepository = get() ,get()) }
    single { EditProjectUseCase(get(),get()) }
    single { DeleteProjectUseCase(projectsRepository = get()) }
    single { GetProjectByIdUseCase(projectsRepository = get()) }
    single { GetAllProjectsUseCase(projectsRepository = get()) }
    single { AddLogUseCase(get()) }
    // endregion

    // region Tasks
    single { CreateTaskUseCase(get() ,get()) }
    single { EditTaskUseCase(get(),get()) }
    single { DeleteTaskUseCase(get()) }
    single { GetTaskBytIdUseCase(get()) }
    single { GetAllTasksByProjectIdUseCase(get()) }
    // endregion

    // region Logs
    single { GetTaskLogsUseCase(get()) }
    single { GetProjectLogUseCase(get()) }
    single { AddLogUseCase(get()) }
    // endregion

    // region Users
    single { CreateUserUseCase(get()) }
    single { EditUserUseCase(get()) }
    single { LoginUserUseCase(get()) }
    single { DeleteUserUseCase(get()) }
    single { GetAllUsersUseCase(get()) }
    // endregion

    // region States
    single { GetAllStatesUseCase(get()) }
    single { CreateStateUseCase(get()) }
    single { EditStateUseCase(get()) }
    // endregion
}