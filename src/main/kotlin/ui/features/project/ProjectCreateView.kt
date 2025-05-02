package ui.features.project


import logic.model.Project
import logic.model.User
import logic.usecase.project.CreateProjectUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class ProjectCreateView(
    private val createProjectUseCase: CreateProjectUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun createProject(user: User) {
        outputFormatter.printHeader("Create a New Project")

        val title = inputHandler.promptForInput("Enter project title: ")
        val description = inputHandler.promptForInput("Enter project description: ")
        //current user
        //enter list of task or skip
        //enter state
       // val project = Project(UUID.randomUUID(), title, description , )

      //  val result = createProjectUseCase.createProject(project, user)
//        result.fold(
//            { outputFormatter.printSuccess("Project created successfully!") },
//            { error -> outputFormatter.printError("Failed to create project: ${error.message}") }
//        )
    }
}
