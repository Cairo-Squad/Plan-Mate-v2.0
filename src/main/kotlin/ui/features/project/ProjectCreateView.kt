package ui.features.project

import logic.model.Project
import logic.model.Task
import logic.model.State
import logic.model.User
import logic.usecase.project.CreateProjectUseCase
import ui.features.auth.UserSession
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class ProjectCreateView(
    private val createProjectUseCase: CreateProjectUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun createProject() {
        outputFormatter.printHeader("Create a New Project")

        val currentUser = UserSession.getUser()
        if (currentUser == null) {
            outputFormatter.printError("No authenticated user found! Please log in first.")
            return
        }

        val title = inputHandler.promptForInput("Enter project title: ")
        val description = inputHandler.promptForInput("Enter project description: ")

        val stateTitle = inputHandler.promptForInput("Enter project state title: ")
        val projectID=UUID.randomUUID()
        val projectState = State(UUID.randomUUID(), stateTitle)
        val tasks = mutableListOf<Task>()
        val addTasks = inputHandler.promptForYesNo("Do you want to add tasks to this project?")
        if (addTasks) {
            while (true) {
                outputFormatter.printHeader("Add a Task")
                val taskTitle = inputHandler.promptForInput("Task title: ")
                val taskDescription = inputHandler.promptForInput("Task description: ")
                val taskState = State(UUID.randomUUID(),inputHandler.promptForInput("Task state: "))

                tasks.add(Task(UUID.randomUUID(), taskTitle, taskDescription,taskState,projectID ))

                val addAnother = inputHandler.promptForYesNo("Do you want to add another task?")
                if (!addAnother) break
            }
        }

        val project =  Project(
            title = title,
            description = description,
            createdBy = currentUser.id,
            tasks = tasks,
            state = projectState
        )


        val result = createProjectUseCase.createProject(project, currentUser)
        if (result.isSuccess) {
            outputFormatter.printSuccess("Project created successfully!")
        } else {
            outputFormatter.printError("Failed to create project: ${result.exceptionOrNull()?.message}")
        }
    }
}
