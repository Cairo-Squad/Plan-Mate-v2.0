package ui.features.task

import kotlinx.coroutines.runBlocking
import logic.model.Project
import logic.model.Task
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
	fun getAllTasksByProject() = runBlocking {
		displayHeader()
		
		val projects = fetchProjects()
		if (projects.isEmpty()) {
			handleNoProjects()
			return@runBlocking
		}
		
		displayProjects(projects)
		val selectedProject = selectProject(projects)
		
		displayProjectTasks(selectedProject)
		
		inputHandler.waitForEnter()
	}
	
	private fun displayHeader() {
		outputFormatter.printHeader(
			"""
            ╔══════════════════════════╗
            ║ 🏗️  Project Swimlanes View ║
            ╚══════════════════════════╝
            """.trimIndent()
		)
	}
	
	private suspend fun fetchProjects(): List<Project> {
		return getAllProjectUseCase.getAllProjects()
	}
	
	private fun handleNoProjects() {
		outputFormatter.printError("❌ No projects available!")
	}
	
	private fun displayProjects(projects: List<Project>) {
		outputFormatter.printInfo("📌 Available Projects:")
		projects.forEachIndexed { index, project ->
			outputFormatter.printInfo("📂 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
		}
	}
	
	private fun selectProject(projects: List<Project>): Project {
		val projectIndex = inputHandler.promptForIntChoice(
			"🔹 Choose a project to view tasks:", 1..projects.size
		) - 1
		return projects[projectIndex]
	}
	
	private suspend fun displayProjectTasks(selectedProject: Project) {
		val tasks = fetchProjectTasks(selectedProject)
		
		if (tasks.isEmpty()) {
			handleNoTasks(selectedProject)
		} else {
			outputFormatter.printHeader("📌 Tasks in Project: ${selectedProject.title}")
			displayTasks(tasks)
		}
	}
	
	private suspend fun fetchProjectTasks(project: Project): List<Task> {
		return getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!)
	}
	
	private fun handleNoTasks(project: Project) {
		outputFormatter.printWarning("⚠️ No tasks found for project '${project.title}'!")
	}
	
	private fun displayTasks(tasks: List<Task>) {
		tasks.forEach { task ->
			outputFormatter.printInfo("✅ ${task.title} | 🏷️ Status: ${task.state?.title}")
		}
	}
}