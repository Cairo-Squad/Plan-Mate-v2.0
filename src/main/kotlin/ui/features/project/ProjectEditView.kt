package ui.features.project

import logic.model.Project
import logic.usecase.project.EditProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import ui.features.task.EditTaskView
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class ProjectEditView(
    private val editProjectUseCase: EditProjectUseCase,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val editTaskView: EditTaskView // Inject EditTaskView for task editing
) {
    fun editProject() {
        outputFormatter.printHeader("Edit Project")

        val projects = getAllProjectsUseCase.getAllProjects().getOrElse {
            outputFormatter.printError("Failed to retrieve projects.")
            return
        }

        if (projects.isEmpty()) {
            outputFormatter.printError("No projects available to edit.")
            return
        }

        outputFormatter.printHeader("Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})")
        }

        val projectIndex = inputHandler.promptForIntChoice("Select the project number to edit: ", 1..projects.size)
        val selectedProject = projects[projectIndex - 1]

        // Edit basic project info
        val editBasicInfo = inputHandler.promptForYesNo("Do you want to edit basic project information?")
        var newTitle = selectedProject.title
        var newDescription = selectedProject.description

        if (editBasicInfo) {
            newTitle = inputHandler.promptForInput("Enter new project title (leave empty to keep current): ")
                .takeIf { it.isNotBlank() } ?: selectedProject.title

            newDescription = inputHandler.promptForInput("Enter new project description (leave empty to keep current): ")
                .takeIf { it.isNotBlank() } ?: selectedProject.description
        }

        // Ask the user if they want to edit tasks
        val editTasks = inputHandler.promptForYesNo("Do you want to edit tasks within this project?")
        if (editTasks) {
            editTaskView.editTask() // Call the existing EditTaskView method
        }

        // Create updated project
        val updatedProject = selectedProject.copy(
            title = newTitle,
            description = newDescription
        )

        val result = editProjectUseCase.editProject(updatedProject)
        result.fold(
            { outputFormatter.printSuccess("Project updated successfully!") },
            { error -> outputFormatter.printError("Failed to update project: ${error.message}") }
        )
    }
}
