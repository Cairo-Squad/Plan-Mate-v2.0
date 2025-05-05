package ui.features.task

import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class SwimlanesView(
	private val inputHandler: InputHandler,
	private val outputFormatter: OutputFormatter,
	private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
	private val getAllProjectUseCase: GetAllProjectsUseCase
) {
	fun getAllTasksByProject() {
		outputFormatter.printHeader(
			"""
            ╔══════════════════════════╗
            ║ 🏗️  Project Swimlanes View ║
            ╚══════════════════════════╝
            """.trimIndent()
		)

		val projects = getAllProjectUseCase.getAllProjects().getOrNull()

		if (projects.isNullOrEmpty()) {
			outputFormatter.printError("❌ No projects available!")
			return
		}

		outputFormatter.printInfo("📌 Available Projects:")
		projects.forEachIndexed { index, project ->
			outputFormatter.printInfo("📂 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
		}

		val projectIndex = inputHandler.promptForIntChoice(
			"🔹 Choose a project to view tasks:", 1..projects.size
		) - 1

		val selectedProject = projects[projectIndex]

		val tasks = getAllTasksByProjectIdUseCase.execute(selectedProject.id).getOrNull()

		if (tasks.isNullOrEmpty()) {
			outputFormatter.printWarning("⚠️ No tasks found for project '${selectedProject.title}'!")
		} else {
			outputFormatter.printHeader("📌 Tasks in Project: ${selectedProject.title}")
			tasks.forEach { task ->
				outputFormatter.printInfo("✅ ${task.title} | 🏷️ Status: ${task.state.title}")
			}
		}

		inputHandler.waitForEnter()
	}
}
