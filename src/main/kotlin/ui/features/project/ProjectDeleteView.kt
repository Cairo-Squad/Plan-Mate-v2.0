package ui.features.project

import logic.model.Project
import logic.usecase.project.DeleteProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class ProjectDeleteView(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun deleteProject() {
        outputFormatter.printHeader("Delete Project")


        val projects = getAllProjectsUseCase.getAllProjects().getOrElse {
            outputFormatter.printError("Failed to retrieve projects.")
            return
        }

        if (projects.isEmpty()) {
            outputFormatter.printError("No projects available to delete.")
            return
        }

        outputFormatter.printHeader("Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})")
        }


        val projectIndex = inputHandler.promptForIntChoice("Select the project number to delete: ", 1..projects.size)
        val selectedProject = projects[projectIndex - 1]


        val confirm = inputHandler.promptForYesNo("Are you sure you want to delete project '${selectedProject.title}'?")

        if (confirm) {
            val result = deleteProjectUseCase.deleteProjectById(selectedProject.id)
            result.fold(
                { outputFormatter.printSuccess("Project deleted successfully!") },
                { error -> outputFormatter.printError("Failed to delete project: ${error.message}") }
            )
        }
    }
}
