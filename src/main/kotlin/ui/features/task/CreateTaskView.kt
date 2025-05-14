package ui.features.task

import kotlinx.coroutines.runBlocking
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.usecase.project.EditProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.state.CreateStateUseCase
import logic.usecase.state.GetAllStatesUseCase
import logic.usecase.task.CreateTaskUseCase
import logic.usecase.task.GetAllTasksUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class CreateTaskView(
    private val createTaskUseCase: CreateTaskUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val editProjectUseCase: EditProjectUseCase,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val createStateUseCase: CreateStateUseCase,
    private val getAllStatesUseCase: GetAllStatesUseCase,
	private val getAllTasksUseCase:GetAllTasksUseCase
) {
    lateinit var projects: List<Project>

    fun createTask() = runBlocking {
        outputFormatter.printHeader("Create a New Task")

        val title = inputHandler.promptForInput("Enter task title: ")
        val description = inputHandler.promptForInput("Enter task description: ")
	    
	    if (title.isEmpty()){
			println("title cannot be empty")
		}

        try {
            fetchProjects()

            if (projects.isEmpty()) {
                outputFormatter.printError("No projects available. Please create a project first.")
                return@runBlocking
            }
            val taskState = createTaskState()
            val selectedProject = selectProject()

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
        val stateTitle = inputHandler.promptForInput("Enter state title: ")
        val isTaskState = createStateUseCase.createState(State(title = stateTitle))
        if (isTaskState){
            val taskStateCreated = getAllStatesUseCase.getAllStateById().last()
            return taskStateCreated
        }
        return State()
    }

    private suspend fun createAndUpdateProject(task: Task, selectedProject: Project) {
        try {
            val isCreatedTask = createTaskUseCase.createTask(task)
	        getAllTasksUseCase.getAllTasks().last()
            if (isCreatedTask){
                val updatedProject = selectedProject.copy(
                    id = selectedProject.id,
                )
                
                editProjectUseCase.editProject(updatedProject)
                outputFormatter.printSuccess("Task created successfully!")
            }
        } catch (ex: Exception) {
            outputFormatter.printError(ex.message ?: "failed to create task!!")
        }
    }
}

