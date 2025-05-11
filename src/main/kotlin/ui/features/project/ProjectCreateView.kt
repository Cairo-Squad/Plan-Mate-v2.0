package ui.features.project

import kotlinx.coroutines.runBlocking
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.usecase.project.CreateProjectUseCase
import logic.usecase.state.CreateStateUseCase
import logic.usecase.task.CreateTaskUseCase
import logic.usecase.user.GetCurrentUserUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class ProjectCreateView(
	private val createProjectUseCase: CreateProjectUseCase,
	private val inputHandler: InputHandler,
	private val outputFormatter: OutputFormatter,
	private val createStateUseCase: CreateStateUseCase,
	private val createTaskUseCase: CreateTaskUseCase,
    private val getCurrentUserUseCase : GetCurrentUserUseCase
) {
	fun createProject() = runBlocking {
		displayHeader()

		val currentUser = validateUserAuthentication() ?: return@runBlocking

		val (title, description) = collectProjectInfo()
		val projectState = createInitialState()

		val project = buildProject(title, description, currentUser.id!!, projectState)

		try {
			val projectId = createProjectUseCase.createProject(project, currentUser)
			outputFormatter.printSuccess("✅ Project '${title}' created successfully! 🎉")

			handleTaskCreation(projectId)
		} catch (ex: Exception) {
			outputFormatter.printError("❌ Failed to create project: ${ex.message}")
		}
	}

	private fun displayHeader() {
		outputFormatter.printHeader(
			"""
            ╔════════════════════════════╗
            ║ 🏗️ Create a New Project ║
            ╚════════════════════════════╝
            """.trimIndent()
		)
	}

	private suspend fun validateUserAuthentication() = getCurrentUserUseCase.getCurrentUser().also {
		if (it == null) {
			outputFormatter.printError("❌ No authenticated user found! Please log in first.")
		}
	}

	private fun collectProjectInfo(): Pair<String, String> {
		val title = inputHandler.promptForInput("📂 Enter project title: ")
		val description = inputHandler.promptForInput("📝 Enter project description: ")
		return title to description
	}

	private fun createInitialState(): State = runBlocking {
		val stateTitle = inputHandler.promptForInput("📊 Enter initial project state: ")
		val projectState = State(UUID.randomUUID(), stateTitle)
		createStateUseCase.createState(projectState)
		return@runBlocking projectState
	}

	private fun buildProject(title: String, description: String, userId: UUID, state: State): Project {
		return Project(
			title = title, description = description, createdBy = userId, tasks = emptyList(), state = state
		)
	}

	private fun handleTaskCreation(projectId: UUID) {
		val addTasks = inputHandler.promptForYesNo("Do you want to add tasks to this project?")

		if (addTasks) {
			while (true) {
				createTaskForProject(projectId)

				val addAnother = inputHandler.promptForYesNo("➕ Do you want to add another task?")
				if (!addAnother) break
			}
		}
	}

	private fun createTaskForProject(projectId: UUID) = runBlocking {
		outputFormatter.printHeader("📌 Add a Task")

		val taskTitle = inputHandler.promptForInput("✅ Task title: ")
		val taskDescription = inputHandler.promptForInput("📝 Task description: ")

		val taskState = createTaskState()

		val task = Task(
			id = UUID.randomUUID(), // TODO provide the id from data layer
			title = taskTitle, description = taskDescription, state = taskState, projectId = projectId
		)

		createTaskUseCase.createTask(task)
	}

	private fun createTaskState(): State = runBlocking{
		val taskStateTitle = inputHandler.promptForInput("📊 Task state: ")
		val taskState = State(UUID.randomUUID(), taskStateTitle)
		createStateUseCase.createState(taskState)
		return@runBlocking taskState
	}
}
