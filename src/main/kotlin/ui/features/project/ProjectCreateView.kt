package ui.features.project

import kotlinx.coroutines.runBlocking
import logic.model.Project
import logic.model.State
import logic.usecase.project.CreateProjectUseCase
import logic.usecase.state.CreateStateUseCase
import logic.usecase.state.GetAllStatesUseCase
import logic.usecase.user.GetCurrentUserUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class ProjectCreateView(
	private val createProjectUseCase: CreateProjectUseCase,
	private val inputHandler: InputHandler,
	private val outputFormatter: OutputFormatter,
	private val createStateUseCase: CreateStateUseCase,
    private val getCurrentUserUseCase : GetCurrentUserUseCase,
	private val getAllStatesUseCase: GetAllStatesUseCase
) {
	fun createProject() = runBlocking {
		displayHeader()

		val currentUser = validateUserAuthentication() ?: return@runBlocking

		val (title, description) = collectProjectInfo()
		val projectState = createInitialState()

		val project = buildProject(title, description, currentUser.id!!, projectState)

		try {
			val isProjectCreated = createProjectUseCase.createProject(project, currentUser)
			if (isProjectCreated) {
				outputFormatter.printSuccess("✅ Project '${title}' created successfully! 🎉")
			}
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
		val isProjectStateCreated = createStateUseCase.createState(State(title = stateTitle))
		if (isProjectStateCreated){
			val projectState = getAllStatesUseCase.getAllStateById().last()
			return@runBlocking projectState
		}
		return@runBlocking State()
	}

	private fun buildProject(title: String, description: String, userId: UUID, state: State): Project {
		return Project(
			title = title, description = description, createdBy = userId, tasks = emptyList(), state = state
		)
	}
}
