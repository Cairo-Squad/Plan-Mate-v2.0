package ui.features.project

import kotlinx.coroutines.runBlocking
import logic.model.Project
import logic.usecase.project.EditProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import ui.features.task.EditTaskView
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class ProjectEditView(
    private val editProjectUseCase: EditProjectUseCase,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val editTaskView: EditTaskView
) {
    lateinit var projects: List<Project>
    
    fun editProject() = runBlocking {
        displayHeader()
        
        if (!loadProjects()) {
            return@runBlocking
        }
        
        if (handleEmptyProjectsList()) {
            return@runBlocking
        }
        
        displayAvailableProjects()
        
        val selectedProject = selectProjectToEdit()
        
        val updatedProject = updateProjectInformation(selectedProject)
        
        saveUpdatedProject(updatedProject)
    }
    
    private fun displayHeader() {
        outputFormatter.printHeader("Edit Project")
    }
    
    private fun loadProjects(): Boolean = runBlocking{
	    return@runBlocking try {
		    projects = getAllProjectsUseCase.getAllProjects()
		    true
	    } catch (ex: Exception) {
		    outputFormatter.printError(ex.message ?: "Failed to retrieve projects.")
		    false
	    }
    }
    
    private fun handleEmptyProjectsList(): Boolean {
        if (projects.isEmpty()) {
            outputFormatter.printError("No projects available to edit.")
            return true
        }
        return false
    }
    
    private fun displayAvailableProjects() {
        outputFormatter.printHeader("Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})")
        }
    }
    
    private fun selectProjectToEdit(): Project {
        val projectIndex = inputHandler.promptForIntChoice("Select the project number to edit: ", 1..projects.size)
        return projects[projectIndex - 1]
    }
    
    private fun updateProjectInformation(project: Project): Project = runBlocking{
        var newTitle = project.title
        var newDescription = project.description
        
        val editBasicInfo = inputHandler.promptForYesNo("Do you want to edit basic project information?")
        
        if (editBasicInfo) {
            newTitle = promptForNewTitle(project.title?: "")
            newDescription = promptForNewDescription(project.description?: "")
            
            handleTaskEditing()
        }
	    
	    return@runBlocking project.copy(
		    title = newTitle,
		    description = newDescription,
	    )
    }
    
    private fun promptForNewTitle(currentTitle: String): String {
        return inputHandler.promptForInput("Enter new project title (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: currentTitle
    }
    
    private fun promptForNewDescription(currentDescription: String): String {
        return inputHandler.promptForInput("Enter new project description (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: currentDescription
    }
    
    private fun handleTaskEditing() {
        val editTasks = inputHandler.promptForYesNo("Do you want to edit tasks within this project?")
        if (editTasks) {
            editTaskView.editTask()
        }
    }
    
    private fun saveUpdatedProject(updatedProject: Project) = runBlocking{
        try {
            editProjectUseCase.editProject(updatedProject)
            outputFormatter.printSuccess("Project updated successfully!")
        } catch (ex: Exception) {
            outputFormatter.printError("Failed to update project: ${ex.message}")
        }
    }
}