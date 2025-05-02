package ui.features.project

import logic.usecase.project.GetProjectByIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class ProjectDetailView(
    private val getProjectByIdUseCase: GetProjectByIdUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun viewProjectDetails() {
        outputFormatter.printHeader("Project Details")
            //iterate over all projects

        val projectId = UUID.fromString(inputHandler.promptForInput("Enter Project ID to view details: "))
        val project = getProjectByIdUseCase.getProjectById(projectId).getOrElse {
            outputFormatter.printError("Project not found!")
            return
        }

        outputFormatter.printInfo("Project ID: ${project.id}")
        outputFormatter.printInfo("Title: ${project.title}")
        outputFormatter.printInfo("Description: ${project.description}")

        inputHandler.waitForEnter()
    }
}
