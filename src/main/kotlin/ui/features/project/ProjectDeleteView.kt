package ui.features.project

import kotlinx.coroutines.runBlocking
import logic.usecase.project.DeleteProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class ProjectDeleteView(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun deleteProject() = runBlocking {
        displayHeader()
        
        val projects = getAllProjectsUseCase.getAllProjects()
        
        if (projects.isEmpty()) {
            handleEmptyProjectsList()
            return@runBlocking
        }
        
        displayAvailableProjects(projects)
        
        val selectedProject = selectProjectForDeletion(projects)
        
        if (confirmDeletion(selectedProject.title?: "")) {
            performProjectDeletion(selectedProject.id!!, selectedProject.title?: "")
        }
        
        inputHandler.waitForEnter()
    }
    
    private fun displayHeader() {
        outputFormatter.printHeader(
            """
            ╔══════════════════════════════╗
            ║ 🗑️  Project Deletion Menu      ║
            ╚══════════════════════════════╝
            """.trimIndent()
        )
    }
    
    private fun handleEmptyProjectsList() {
        outputFormatter.printError("❌ No projects available for deletion!")
    }
    
    private fun displayAvailableProjects(projects: List<logic.model.Project>) {
        outputFormatter.printInfo("📂 Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
        }
    }
    
    private fun selectProjectForDeletion(projects: List<logic.model.Project>): logic.model.Project {
        val projectIndex = inputHandler.promptForIntChoice("🔹 Select a project to delete:", 1..projects.size) - 1
        return projects[projectIndex]
    }
    
    private fun confirmDeletion(projectTitle: String): Boolean {
        outputFormatter.printWarning("⚠️ Are you sure you want to delete '${projectTitle}'? This action **cannot be undone**.")
        val confirmation = inputHandler.promptForInput("Type 'YES' to confirm deletion: ")
        return confirmation.equals("YES", ignoreCase = true)
    }
    
    private fun performProjectDeletion(projectId: UUID, projectTitle: String) = runBlocking {
        try {
            val result = deleteProjectUseCase.deleteProjectById(projectId)
            outputFormatter.printSuccess("✅ Project '${projectTitle}' deleted successfully!")
        } catch (ex: Exception) {
            outputFormatter.printError("❌ Failed to delete project: ${ex.message}")
        }
    }
}