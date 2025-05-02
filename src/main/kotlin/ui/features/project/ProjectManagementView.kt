package ui.features.project

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
                    "4. View Project Logs",
                    "5. View All Projects",
                    "6. Exit"
                )
            )

            when (inputHandler.promptForIntChoice("Select an option: ", 1..6)) {
                1 -> projectCreateView.createProject()
                2 -> projectEditView.editProject()
                3 -> projectDeleteView.deleteProject()
                4 -> projectLogView.viewProjectLogs()
                5 -> projectDetailView.viewProjectDetails()
                6 -> return
            }
        }
    }
}
