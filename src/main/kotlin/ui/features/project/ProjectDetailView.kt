package ui.features.project

import logic.model.Task
import logic.usecase.project.GetAllProjectsUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
class ProjectDetailView(
	private val getAllProjectsUseCase: GetAllProjectsUseCase,
	private val inputHandler: InputHandler,
	private val outputFormatter: OutputFormatter
) {
	fun viewProjectDetails() {
		outputFormatter.printHeader(
			"""
            ╔══════════════════════════╗
            ║ 🏗️  Project Details Viewer  ║
            ╚══════════════════════════╝
            """.trimIndent()
		)

		val projects = getAllProjectsUseCase.getAllProjects()
		if (projects.isEmpty()) {
			outputFormatter.printError("❌ No projects available to view!")
			return
		}

		outputFormatter.printInfo("📂 Available Projects:")
		projects.forEachIndexed { index, project ->
			outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
		}

		val userContinue = inputHandler.promptForYesNo("🔍 Would you like to view details for a specific project?")

		if (userContinue) {
			val projectIndex = inputHandler.promptForIntChoice("🔹 Select a project number:", 1..projects.size) - 1
			val selectedProject = projects[projectIndex]

			outputFormatter.printHeader("📜 Project Information")
			outputFormatter.printInfo("🆔 Project ID: ${selectedProject.id}")
			outputFormatter.printInfo("📂 Title: ${selectedProject.title}")
			outputFormatter.printInfo("📝 Description: ${selectedProject.description}")
			outputFormatter.printInfo("📊 State: ${selectedProject.state.title}")
			outputFormatter.printInfo("✅ Tasks: \n${displayTasksOnUiFormatter(selectedProject.tasks)}")

			inputHandler.waitForEnter()
		}
	}
	
	
	private fun displayTasksOnUiFormatter(tasks: List<Task>): String {
		if (tasks.isEmpty()) return "⚠️ No tasks available."
		
		return tasks.mapIndexed { index, task ->
			"🔹 ${index + 1}. ${task.title} [${task.state.title}]"
		}.joinToString("\n")
	}
}
