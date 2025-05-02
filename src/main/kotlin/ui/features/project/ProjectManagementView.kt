package ui.features.project

import logic.model.User
import ui.features.log.ProjectLogView
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class ProjectManagementView(
    private val projectCreateView: ProjectCreateView,
    private val projectEditView: ProjectEditView,
    private val projectDeleteView: ProjectDeleteView,
    private val projectDetailView: ProjectDetailView,
    private val projectLogView: ProjectLogView,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun showProjectMenu() {
        while (true) {
            outputFormatter.printHeader("Project Management")
            outputFormatter.printMenu(
                listOf(
                    "1. Create Project",
                    "2. Edit Project",
                    "3. Delete Project",
                    "4. View Project Details",
                    "5. View Project Logs",
                    "6. View All Projects",
                    "7. Exit"
                )
            )

            when (inputHandler.promptForIntChoice("Select an option: ", 1..7)) {
              //  1 -> projectCreateView.createProject()
                2 -> projectEditView.editProject()
                3 -> projectDeleteView.deleteProject()
                4 -> projectDetailView.viewProjectDetails()
                5 -> projectLogView.viewProjectLogs()
                6 -> projectDeleteView.deleteProject()
                7 -> return
            }
        }
    }
}
