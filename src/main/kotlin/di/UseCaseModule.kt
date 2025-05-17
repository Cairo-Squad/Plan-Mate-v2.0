package di

import logic.usecase.log.*
import logic.usecase.project.*
import logic.usecase.state.*
import logic.usecase.task.*
import logic.usecase.user.*
import org.koin.dsl.module

val useCasesModule = module {
    // region Projects
    single { CreateProjectUseCase(projectRepository = get(), addProjectLogUseCase = get(), validationProject = get()) }
    single { EditProjectUseCase(get(), get(), get()) }
    single {
        DeleteProjectUseCase(
            projectsRepository = get(),
            addProjectLogUseCase = get(),
            getProjectByIdUseCase = get()
        )
    }
    single { GetProjectByIdUseCase(projectsRepository = get()) }
    single { GetAllProjectsUseCase(projectsRepository = get()) }
    single { AddTaskLogUseCase(taskLogsRepository = get()) }
    single { AddProjectLogUseCase(projectLogsRepository = get()) }
    // endregion

    // region Tasks
    single { CreateTaskUseCase(tasksRepository = get(), addTaskLogUseCase = get(), getCurrentUserUseCase = get(), validationTask = get()) }
    single { EditTaskUseCase(tasksRepository = get(), addTaskLogUseCase = get(), getCurrentUserUseCase = get()) }
    single { DeleteTaskUseCase(tasksRepository = get(), getCurrentUserUseCase = get(), addTaskLogUseCase = get()) }
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
    single { SignUpUseCase(get(), get()) }
    single { EditUserUseCase(get()) }
    single { LoginUserUseCase(get(), get()) }
    single { DeleteUserUseCase(get()) }
    single { GetAllUsersUseCase(get()) }
    single { GetCurrentUserUseCase(get()) }
    // endregion

    // region States
    single { GetAllStatesUseCase(get()) }
    single { CreateStateUseCase(get(), get()) }
    single { EditStateUseCase(get(), get()) }
    single { GetStateByIdUseCase(get()) }
    // endregion

    // region validation
    single { ValidationProject() }
    single { ValidationTask() }
    single { ValidationState() }
    // endregion
}