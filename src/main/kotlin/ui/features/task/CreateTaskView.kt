package ui.features.task

import kotlinx.coroutines.runBlocking
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.usecase.project.EditProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.state.CreateStateUseCase
import logic.usecase.task.CreateTaskUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class CreateTaskView(
	private val createTaskUseCase: CreateTaskUseCase,
	private val inputHandler: InputHandler,
	private val outputFormatter: OutputFormatter,
	private val editProjectUseCase: EditProjectUseCase,
	private val getAllProjectsUseCase: GetAllProjectsUseCase,
	private val createStateUseCase: CreateStateUseCase
) {
	lateinit var projects: List<Project>
	
	fun createTask() = runBlocking {
		outputFormatter.printHeader("Create a New Task")
		
		val title = inputHandler.promptForInput("Enter task title: ")
		val description = inputHandler.promptForInput("Enter task description: ")
		
		try {
			fetchProjects()
			
			if (projects.isEmpty()) {
				outputFormatter.printError("No projects available. Please create a project first.")
				return@runBlocking
			}
			
			val selectedProject = selectProject()
			val taskState = createTaskState()
			
			val task = Task(
				title = title,
				description = description,
				state = taskState,
				projectId = selectedProject.id!!
			)
			
			createAndUpdateProject(task, selectedProject)
			
		} catch (ex: Exception) {
			outputFormatter.printError("Failed to get all projects: ${ex.message}")
		}
	}
	
	private suspend fun fetchProjects() {
		projects = getAllProjectsUseCase.getAllProjects()
	}
	
	private fun selectProject(): Project {
		projects.forEachIndexed { index, project ->
			outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})")
		}
		
		val projectIndex = inputHandler.promptForIntChoice(
			"Select the number of project that you want to add task to : ",
			1..projects.size
		) - 1
		return projects[projectIndex]
	}
	
	private suspend fun createTaskState(): State {
		val taskState = State(title =  "TODO")
		val finalStateOfTask =createStateUseCase.createState(taskState)
		return taskState
	}
	
	private suspend fun createAndUpdateProject(task: Task, selectedProject: Project) {
		try {
			val createdTask = createTaskUseCase.createTask(task)
			val updatedProject = selectedProject.copy(
				id = selectedProject.id,
				tasks = selectedProject.tasks?.plus(createdTask)
			)
			
			editProjectUseCase.editProject(updatedProject)
			outputFormatter.printSuccess("Task created successfully!")
		} catch (ex: Exception) {
			outputFormatter.printError(ex.message ?: "failed to create task!!")
		}
	}
}

