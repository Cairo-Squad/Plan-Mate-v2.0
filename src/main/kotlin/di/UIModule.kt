package di

import org.koin.dsl.module
import ui.features.auth.LoginManagementView
import ui.features.log.LogManagementView
import ui.features.log.ProjectLogView
import ui.features.log.TaskLogView
import ui.features.project.*
import ui.features.task.*
import ui.features.user.UserManagementView
import ui.features.user.admin.*
import ui.features.user.mate.MateManagementView
import ui.utils.CLIMenu
import ui.utils.InputHandler
import ui.utils.OutputFormatter

val uiModule = module {
    single { ProjectEditView(get(), get(), get(), get(), get()) }

    single { ProjectDeleteView(get(), get(), get(), get()) }

    single { ProjectDetailView(get(), get(), get()) }

    single { ProjectLogView(get(), get(), get(), get()) }

    single { TaskLogView(get(), get(), get(), get(), get()) }

    single { InputHandler() }

    single { OutputFormatter() }

    single { ProjectCreateView(get(), get(), get(), get(), get()) }

    single { ProjectManagementView(get(), get(), get(), get(), get(), get(), get()) }

    single { AdminManagementView(get(), get(), get(), get(), get(), get(), get(), get()) }

    single { LogManagementView(get(), get(), get(), get()) }

    single { MateManagementView(get(), get()) }

    single { CreateTaskView(get(), get(), get(), get(), get(), get()) }

    single { DeleteTaskView(get(), get(), get(), get(), get()) }

    single { EditTaskView(get(), get(), get(), get(), get(), get()) }

    single { TaskManagementView(get(), get(), get(), get(), get(), get(), get()) }

    single { UserManagementView(get(), get()) }

    single {
        LoginManagementView(get(), get(), get(), get())
    }

    single { CLIMenu(get(), get(), get()) }

    single { SwimlanesView(get(), get(), get(), get()) }

    single { CreateNewUserView(get(), get(), get()) }

    single { EditUserView(get(), get(), get(), get()) }

    single { DeleteUserView(get(), get(), get(), get()) }

    single {
        ListAllUsersView(get(), get(), get())
    }
}