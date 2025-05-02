package ui.features.task

import org.koin.core.component.inject
import ui.utils.BaseCLIView
import ui.utils.TaskDisplayInfo
import logic.service.TaskService
import logic.service.ProjectService
import logic.service.StateService
import logic.model.Project

/**
 * View for displaying tasks in swimlanes format
 */
class SwimlanesView : BaseCLIView() {
    private val taskService: TaskService by inject()
    private val projectService: ProjectService by inject()
    private val stateService: StateService by inject()

    override fun display(vararg params: Any) {
        if (params.isEmpty() || params[0] !is String) {
            showError("Project ID is required")
            pressEnterToContinue()
            navigator.navigateBack()
            return
        }

        val projectId = params[0] as String
        val project = projectService.getProjectById(projectId)

        if (project == null) {
            showError("Project not found")
            pressEnterToContinue()
            navigator.navigateBack()
            return
        }

        displayProjectTasks(project)
    }

    override fun handleInput(input: String): Boolean {
        when (input.trim().lowercase()) {
            "back", "b" -> {
                navigator.navigateBack()
                return true
            }
            "refresh", "r" -> {
                // Redisplay the current view
                display(*getParams())
                return true
            }
            else -> {
                // Check if input is a task selection
                if (input.startsWith("task ")) {
                    val taskId = input.substring(5).trim()
                    selectTask(taskId)
                    return true
                }
            }
        }
        return false
    }

    private fun getParams(): Array<Any> {
        // This is a placeholder. In a real implementation, you would store
        // the current project ID when display() is called and return it here.
        return arrayOf("")
    }

    private fun displayProjectTasks(project: Project) {
        outputFormatter.printHeader("Project: ${project.name} (${project.id})")

        // Get all states for this project
        val states = stateService.getStatesForProject(project.id)

        if (states.isEmpty()) {
            showError("No states defined for this project")
            pressEnterToContinue()
            return
        }

        // Get all tasks for this project
        val tasks = taskService.getTasksByProject(project.id)

        if (tasks.isEmpty()) {
            println("No tasks found for this project.")
            println("\nCommands:")
            println("  - create: Create a new task")
            println("  - back: Go back to previous screen")
            pressEnterToContinue()
            return
        }

        // Group tasks by state
        val tasksByState = tasks.groupBy { it.state }
            .mapValues { (_, tasksInState) ->
                tasksInState.map { task ->
                    TaskDisplayInfo(
                        id = task.id,
                        title = task.title,
                        assignee = task.assignee ?: "Unassigned"
                    )
                }
            }
            .toMutableMap()

        // Ensure all states have an entry in the map
        states.forEach { state ->
            if (!tasksByState.containsKey(state)) {
                tasksByState[state] = emptyList()
            }
        }

        // Draw the swimlanes
        outputFormatter.drawSwimlanes(states, tasksByState)

        // Display available commands
        println("\nCommands:")
        println("  - task [ID]: Select a task")
        println("  - create: Create a new task")
        println("  - refresh: Refresh the view")
        println("  - back: Go back to previous screen")
    }

    private fun selectTask(taskId: String) {
        val task = taskService.getTaskById(taskId)
        if (task != null) {
            navigator.navigateTo(TaskView::class, task)
        } else {
            showError("Task not found: $taskId")
            pressEnterToContinue()
        }
    }
}