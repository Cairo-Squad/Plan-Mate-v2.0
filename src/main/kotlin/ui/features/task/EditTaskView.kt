package ui.features.task

import kotlinx.coroutines.runBlocking
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.state.EditStateUseCase
import logic.usecase.task.EditTaskUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class EditTaskView(
    private val editTaskUseCase: EditTaskUseCase,
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val editStateUseCase: EditStateUseCase,
) {
    fun editTask() = runBlocking {
        displayHeader()
        
        val projects = fetchProjects()
        if (projects.isEmpty()) {
            handleNoProjects()
            return@runBlocking
        }
        
        displayProjects(projects)
        val selectedProject = selectProject(projects)
        
        val tasks = fetchProjectTasks(selectedProject)
        if (tasks.isEmpty()) {
            handleNoTasks(selectedProject)
            return@runBlocking
        }
        
        displayTasks(tasks)
        val selectedTask = selectTask(tasks)
        
        val editedTaskDetails = promptForTaskDetails(selectedTask)
        
        updateTaskState(editedTaskDetails.state!!)
        updateTask(editedTaskDetails)
        
        outputFormatter.printSuccess("✅ Task '${editedTaskDetails.title}' updated successfully!")
        
        inputHandler.waitForEnter()
    }
    
    private fun displayHeader() {
        outputFormatter.printHeader(
            """
            ╔══════════════════════════╗
            ║ ✏️ Edit Task Information  ║
            ╚══════════════════════════╝
            """.trimIndent()
        )
    }
    
    private suspend fun fetchProjects(): List<Project> {
        return getAllProjectsUseCase.getAllProjects()
    }
    
    private fun handleNoProjects() {
        outputFormatter.printError("❌ No projects available for task editing!")
    }
    
    private fun displayProjects(projects: List<Project>) {
        outputFormatter.printInfo("📂 Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
        }
    }
    
    private fun selectProject(projects: List<Project>): Project {
        val projectIndex = inputHandler.promptForIntChoice(
            "🔹 Choose the number of project to edit its task:", 1..projects.size
        ) - 1
        return projects[projectIndex]
    }
    
    private suspend fun fetchProjectTasks(project: Project): List<Task> {
        return getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!)
    }
    
    private fun handleNoTasks(project: Project) {
        outputFormatter.printWarning("⚠️ No tasks found for project '${project.title}'.")
    }
    
    private fun displayTasks(tasks: List<Task>) {
        outputFormatter.printInfo("📝 Available Tasks:")
        tasks.forEachIndexed { index, task ->
            outputFormatter.printInfo("✅ ${index + 1}. ${task.title} | 🏷️ Status: ${task.state?.title}")
        }
    }
    
    private fun selectTask(tasks: List<Task>): Task {
        val taskIndex = inputHandler.promptForIntChoice("🔹 Select a task to edit:", 1..tasks.size) - 1
        return tasks[taskIndex]
    }
    
    private fun promptForTaskDetails(selectedTask: Task): Task {
        val newTitle = inputHandler.promptForInput("✏️ Enter new title (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedTask.title
        
        val newDescription = inputHandler.promptForInput("📝 Enter new description (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedTask.description
        
        val newStateTitle = inputHandler.promptForInput("🚀 Enter new state (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedTask.state?.title
        
        val newState = State(selectedTask.state?.id!!, newStateTitle!!)
        
        return selectedTask.copy(
            title = newTitle,
            description = newDescription,
            state = newState
        )
    }
    
    private suspend fun updateTaskState(state: State) {
        editStateUseCase.editState(state)
    }
    
    private suspend fun updateTask(task: Task) {
        editTaskUseCase.editTask(task)
    }
}