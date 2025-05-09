package data.dataSource.remoteDataSource.mongo

import com.mongodb.client.MongoDatabase
import data.dto.*
import data.repositories.DataSource
import java.util.*

class RemoteDataSource(
	database: MongoDatabase,
	private val logsHandler: LogsMongoHandlerImpl = LogsMongoHandlerImpl(database),
	private val projectsHandler: ProjectsMongoHandlerImpl = ProjectsMongoHandlerImpl(database),
	private val statesHandler: StatesMongoHandlerImpl = StatesMongoHandlerImpl(database),
	private val tasksHandler: TasksMongoHandlerImpl = TasksMongoHandlerImpl(database),
	private val usersHandler: UsersMongoHandlerImpl = UsersMongoHandlerImpl(database),
) : DataSource {

    override fun getAllUsers() : List<UserDto> {
        return usersHandler.readAll()
    }

    override fun createUser(id : UUID, name : String, password : String, type : UserType) : Boolean {
        val userDto = UserDto(
            id = UUID.randomUUID(),
            name = name,
            password = password,
            type = type
        )

        return usersHandler.write(userDto)

    }

    override fun editUser(user : UserDto) {
        usersHandler.edit(user)
    }

    override fun deleteUser(user : UserDto) {
        usersHandler.delete(user)
    }

    override fun createProject(project : ProjectDto) {
        projectsHandler.write(project)
    }

    override fun editProject(newProject : ProjectDto) {
        projectsHandler.edit(newProject)
    }

    override fun deleteProjectById(project : ProjectDto) {
        projectsHandler.delete(project)
    }

    override fun getProjectById(projectId : UUID) : ProjectDto {
        return projectsHandler.readByEntityId(projectId)
    }

    override fun getAllProjects() : List<ProjectDto> {
        return projectsHandler.readAll()
    }

    override fun getTasksByProjectId(projectId : UUID) : List<TaskDto> {
        return tasksHandler.readAll().filter { it.projectId == projectId }
    }

	override fun createTask(task: TaskDto) {
		tasksHandler.write(task)
	}

	override fun editTask(task: TaskDto) {
		tasksHandler.edit(task)
	}

	override fun deleteTask(task: TaskDto) {
		tasksHandler.delete(task)
	}

	override fun getTaskById(taskID: UUID): TaskDto {
		return tasksHandler.readByEntityId(taskID)
	}
	
	override fun getAllStates(): List<StateDto> {
		return statesHandler.readAll()
	}

	override fun getStateById(stateId: UUID): StateDto {
		return statesHandler.readByEntityId(stateId)
	}

	override fun createState(state: StateDto): Boolean {
		statesHandler.write(state)
		return true
	}

	override fun editState(state: StateDto) {
		statesHandler.edit(state)
	}
	
	override fun recordLog(log: LogDto) {
		logsHandler.write(log)
	}
	
	override fun getTaskLogs(taskId: UUID): List<LogDto> {
		return logsHandler.readAll().filter { it.entityType == EntityType.TASK && it.entityId == taskId }
	}

	override fun getProjectLogs(projectId: UUID): List<LogDto> {
		return logsHandler.readAll()
			.filter { it.entityType == EntityType.PROJECT && it.entityId == projectId }
	}
}