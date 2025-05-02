package ui.features.project


import logic.model.Project
import logic.usecase.project.EditProjectUseCase
import logic.usecase.project.GetProjectByIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class ProjectEditView(
    private val editProjectUseCase: EditProjectUseCase,
    private val getProjectByIdUseCase: GetProjectByIdUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun editProject() {
        outputFormatter.printHeader("Edit Project")
        //iterate all projects

        val projectId = UUID.fromString(inputHandler.promptForInput("Enter Project ID to edit: "))
        val project = getProjectByIdUseCase.getProjectById(projectId).getOrElse {
            outputFormatter.printError("Project not found!")
            return
        }

        val newTitle = inputHandler.promptForInput("Enter new project title (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: project.title
        val newDescription =
            inputHandler.promptForInput("Enter new project description (leave empty to keep current): ")
                .takeIf { it.isNotBlank() } ?: project.description

        val updatedProject = project.copy(title = newTitle, description = newDescription)

        val result = editProjectUseCase.editProject(updatedProject)
        result.fold(
            { outputFormatter.printSuccess("Project updated successfully!") },
            { error -> outputFormatter.printError("Failed to update project: ${error.message}") }
        )
    }
}
