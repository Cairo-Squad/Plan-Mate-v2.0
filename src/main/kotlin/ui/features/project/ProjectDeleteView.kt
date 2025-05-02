package ui.features.project

import logic.usecase.project.DeleteProjectUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class ProjectDeleteView(
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun deleteProject() {
        outputFormatter.printHeader("Delete Project")

        val projectId = UUID.fromString(inputHandler.promptForInput("Enter Project ID to delete: "))
        val confirm = inputHandler.promptForYesNo("Are you sure you want to delete this project?")
         //iterate over all projects
        if (confirm) {
            val result = deleteProjectUseCase.deleteProjectById(projectId)
            result.fold(
                { outputFormatter.printSuccess("Project deleted successfully!") },
                { error -> outputFormatter.printError("Failed to delete project: ${error.message}") }
            )
        }
    }
}
