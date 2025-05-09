package ui.features.project

import kotlinx.coroutines.runBlocking
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
    fun deleteProject() = runBlocking {
        outputFormatter.printHeader(
            """
            ╔══════════════════════════════╗
            ║ 🗑️  Project Deletion Menu      ║
            ╚══════════════════════════════╝
            """.trimIndent()
        )

        val projects = getAllProjectsUseCase.getAllProjects()

        if (projects.isEmpty()) {
            outputFormatter.printError("❌ No projects available for deletion!")
	        return@runBlocking
        }

        outputFormatter.printInfo("📂 Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
        }

        val projectIndex = inputHandler.promptForIntChoice("🔹 Select a project to delete:", 1..projects.size) - 1
        val selectedProject = projects[projectIndex]

        outputFormatter.printWarning("⚠️ Are you sure you want to delete '${selectedProject.title}'? This action **cannot be undone**.")

        val confirmation = inputHandler.promptForInput("Type 'YES' to confirm deletion: ")

        if (confirmation.equals("YES", ignoreCase = true)) {
            try {
                val result = deleteProjectUseCase.deleteProjectById(selectedProject.id)
                outputFormatter.printSuccess("✅ Project '${selectedProject.title}' deleted successfully!")
            } catch (ex: Exception) {

                outputFormatter.printError("❌ Failed to delete project: ${ex.message}")
            }
        }

        inputHandler.waitForEnter()
    }
}
