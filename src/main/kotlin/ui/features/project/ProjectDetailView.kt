package ui.features.project

import kotlinx.coroutines.runBlocking
import logic.model.Project
import logic.model.Task
import logic.usecase.project.GetAllProjectsUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class ProjectDetailView(
	private val getAllProjectsUseCase: GetAllProjectsUseCase,
	private val inputHandler: InputHandler,
	private val outputFormatter: OutputFormatter
) {
	fun viewProjectDetails() = runBlocking {
		displayHeader()
		
		val projects = getAllProjectsUseCase.getAllProjects()
		if (projects.isEmpty()) {
			handleEmptyProjectsList()
			return@runBlocking
		}
		
		displayAvailableProjects(projects)
		
		if (userWantsToViewDetails()) {
			val selectedProject = selectProject(projects)
			displayProjectDetails(selectedProject)
			inputHandler.waitForEnter()
		}
	}
	
	private fun displayHeader() {
		outputFormatter.printHeader(
			"""
            ╔══════════════════════════╗
            ║ 🏗️  Project Details Viewer  ║
            ╚══════════════════════════╝
            """.trimIndent()
		)
	}
	
	private fun handleEmptyProjectsList() {
		outputFormatter.printError("❌ No projects available to view!")
	}
	
	private fun displayAvailableProjects(projects: List<Project>) {
		outputFormatter.printInfo("📂 Available Projects:")
		projects.forEachIndexed { index, project ->
			outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
		}
	}
	
	private fun userWantsToViewDetails(): Boolean {
		return inputHandler.promptForYesNo("🔍 Would you like to view details for a specific project?")
	}
	
	private fun selectProject(projects: List<Project>): Project {
		val projectIndex = inputHandler.promptForIntChoice("🔹 Select a project number:", 1..projects.size) - 1
		return projects[projectIndex]
	}
	
	private fun displayProjectDetails(project: Project) {
		outputFormatter.printHeader("📜 Project Information")
		outputFormatter.printInfo("🆔 Project ID: ${project.id}")
		outputFormatter.printInfo("📂 Title: ${project.title}")
		outputFormatter.printInfo("📝 Description: ${project.description}")
		outputFormatter.printInfo("📊 State: ${project.state?.title}")
		outputFormatter.printInfo("✅ Tasks: \n${displayTasksOnUiFormatter(project.tasks ?: emptyList())}")
	}
	
	private fun displayTasksOnUiFormatter(tasks: List<Task>): String {
		if (tasks.isEmpty()) return "⚠️ No tasks available."
		
		return tasks.mapIndexed { index, task ->
			"🔹 ${index + 1}. ${task.title} [${task.state?.title}]"
		}.joinToString("\n")
	}
}