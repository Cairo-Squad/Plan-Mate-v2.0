package di

import logic.usecase.log.*
import logic.usecase.project.*
import logic.usecase.state.*
import logic.usecase.task.*
import logic.usecase.user.*
import org.koin.dsl.module

val useCasesModule = module {
    // region Projects
    single { CreateProjectUseCase(projectRepository = get() ,get())}
    single { EditProjectUseCase(get(),get(), get()) }
    single { DeleteProjectUseCase(projectsRepository = get()) }
    single { GetProjectByIdUseCase(projectsRepository = get()) }
    single { GetAllProjectsUseCase(projectsRepository = get()) }
    single { AddTaskLogUseCase(taskLogsRepository = get()) }
    single { AddProjectLogUseCase(projectLogsRepository = get()) }
    // endregion

    // region Tasks
    single { CreateTaskUseCase(get() ,get()) }
    single { EditTaskUseCase(get()) }
    single { DeleteTaskUseCase(get()) }
    single { GetTaskBytIdUseCase(get()) }
    single { GetAllTasksByProjectIdUseCase(get()) }
    single { GetAllTasksUseCase(get()) }
    // endregion

    // region Logs
    single { GetTaskLogsUseCase(get()) }
    single { GetProjectLogsUseCase(get()) }
    single { AddTaskLogUseCase(get()) }
    single { AddProjectLogUseCase(get()) }
    // endregion

    // region Users
    single { SignUpUseCase(get(),get()) }
    single { EditUserUseCase(get()) }
    single { LoginUserUseCase(get(), get()) }
    single { DeleteUserUseCase(get()) }
    single { GetAllUsersUseCase(get()) }
    single { GetCurrentUserUseCase(get()) }
    // endregion

    // region States
    single { GetAllStatesUseCase(get()) }
    single { CreateStateUseCase(get(),get()) }
    single { EditStateUseCase(get(),get()) }
    // endregion
    
    // region validationProject
    single { ValidationProject() }
    single { ValidationTask() }
    single {ValidationState() }
    // endregion
}