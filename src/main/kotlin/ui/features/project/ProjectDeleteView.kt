package ui.features.project

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
        outputFormatter.printHeader(
            """
            ╔══════════════════════════════╗
            ║ 🗑️  Project Deletion Menu      ║
            ╚══════════════════════════════╝
            """.trimIndent()
        )

        val projects = getAllProjectsUseCase.getAllProjects().getOrNull()

        if (projects.isNullOrEmpty()) {
            outputFormatter.printError("❌ No projects available for deletion!")
            return
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
            val result = deleteProjectUseCase.deleteProjectById(selectedProject.id)
            result.fold(
                { outputFormatter.printSuccess("✅ Project '${selectedProject.title}' deleted successfully!") },
                { error -> outputFormatter.printError("❌ Failed to delete project: ${error.message}") }
            )
        } else {
            outputFormatter.printInfo("🔄 Action canceled. No project was deleted.")
        }

        inputHandler.waitForEnter()
    }
}
