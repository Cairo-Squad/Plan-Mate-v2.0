package ui.features.task

import kotlinx.coroutines.runBlocking
import logic.model.Project
import logic.model.Task
import logic.usecase.project.EditProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.task.DeleteTaskUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class DeleteTaskView(
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val editProjectUseCase: EditProjectUseCase
) {
    fun deleteTask() = runBlocking {
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
        
        handleTaskDeletion(selectedProject, selectedTask)
        
        inputHandler.waitForEnter()
    }
    
    private fun displayHeader() {
        outputFormatter.printHeader(
            """ ╔══════════════════════════════╗
               ║ 🗑️ Delete Task Management ║
               ╚══════════════════════════════╝ """.trimIndent()
        )
    }
    
    private suspend fun fetchProjects(): List<Project> {
        return getAllProjectsUseCase.getAllProjects()
    }
    
    private fun handleNoProjects() {
        outputFormatter.printError("❌ No projects available for task deletion!")
    }
    
    private fun displayProjects(projects: List<Project>) {
        outputFormatter.printInfo("📂 Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
        }
    }
    
    private fun selectProject(projects: List<Project>): Project {
        val projectIndex = inputHandler.promptForIntChoice(
            "🔹 Choose a project to delete its task:", 1..projects.size
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
        outputFormatter.printInfo("📜 Available Tasks:")
        tasks.forEachIndexed { index, task ->
            outputFormatter.printInfo("✅ ${index + 1}. ${task.title} | 🆔 ID: ${task.id} | 🏷️ Status: ${task.state?.title}")
        }
    }
    
    private fun selectTask(tasks: List<Task>): Task {
        val taskIndex = inputHandler.promptForIntChoice("🔹 Select a task to delete:", 1..tasks.size) - 1
        return tasks[taskIndex]
    }
    
    private suspend fun handleTaskDeletion(selectedProject: Project, selectedTask: Task) {
        outputFormatter.printWarning("⚠️ Are you sure you want to delete '${selectedTask.title}'? This action cannot be undone.")
        val confirmation = inputHandler.promptForInput("Type 'YES' to confirm: ")
        
        if (confirmation.equals("YES", ignoreCase = true)) {
            deleteTaskUseCase.deleteTask(selectedTask)
            
            outputFormatter.printSuccess("✅ Task '${selectedTask.title}' deleted successfully!")
        } else {
            outputFormatter.printInfo("🔄 Action canceled. No task was deleted.")
        }
    }
}