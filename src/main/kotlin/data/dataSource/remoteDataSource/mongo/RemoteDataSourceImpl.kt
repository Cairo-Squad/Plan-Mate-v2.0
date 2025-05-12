package data.dataSource.remoteDataSource.mongo

import com.mongodb.client.MongoDatabase
import data.dataSource.remoteDataSource.RemoteDataSource
import data.dataSource.remoteDataSource.mongo.handler.MongoDBHandler
import data.dto.*
import logic.exception.WriteException
import java.util.*

class RemoteDataSourceImpl(
	private val logsHandler: MongoDBHandler<LogDto>,
	private val projectsHandler: MongoDBHandler<ProjectDto>,
	private val statesHandler: MongoDBHandler<StateDto>,
	private val tasksHandler: MongoDBHandler<TaskDto>,
	private val usersHandler: MongoDBHandler<UserDto>,
) : RemoteDataSource {
	private var currentUser: UserDto? = null

    override suspend fun getAllUsers() : List<UserDto> {
        return usersHandler.readAll()
    }

    override suspend fun createUser(id : UUID, name : String, password : String, type : UserType) : Boolean {
        val userDto = UserDto(
            id = UUID.randomUUID(),
            name = name,
            password = password,
            type = type
        )

        val (success) =  usersHandler.write(userDto)
	    return success

    }

    override suspend fun editUser(user : UserDto) {
        usersHandler.edit(user)
    }

    override suspend fun deleteUser(user : UserDto) {
        usersHandler.delete(user)
    }

	override suspend fun loginUser(name: String, password: String): Boolean {
		val users = usersHandler.readAll()
		val user = users.find { it.name == name && it.password == password }
		setCurrentUser(user)
		return user !=null
	}

	override suspend fun getCurrentUser(): UserDto? {
		return currentUser
	}

	private  fun setCurrentUser(user : UserDto?) {
		currentUser = user
	}


	override suspend fun createProject(project : ProjectDto) : UUID? {
		val projectWithId = project.copy(id = UUID.randomUUID())
		val (success, generatedId) = projectsHandler.write(projectWithId)
		if (success){
			return generatedId
		}else{
			throw WriteException()
		}
    }

    override suspend fun editProject(newProject : ProjectDto) {
        projectsHandler.edit(newProject)
    }

    override suspend fun deleteProjectById(project : ProjectDto) {
        projectsHandler.delete(project)
    }

    override suspend fun getProjectById(projectId : UUID) : ProjectDto {
        return projectsHandler.readByEntityId(projectId)
    }

    override suspend fun getAllProjects() : List<ProjectDto> {
        return projectsHandler.readAll()
    }

    override suspend fun getTasksByProjectId(projectId : UUID) : List<TaskDto> {
        return tasksHandler.readAll().filter { it.projectId == projectId }
    }

	override suspend fun createTask(task: TaskDto) {
		tasksHandler.write(task)
	}

	override suspend fun editTask(task: TaskDto) {
		tasksHandler.edit(task)
	}

	override suspend fun deleteTask(task: TaskDto) {
		tasksHandler.delete(task)
	}

	override suspend fun getTaskById(taskID: UUID): TaskDto {
		return tasksHandler.readByEntityId(taskID)
	}
	
	override suspend fun getAllStates(): List<StateDto> {
		return statesHandler.readAll()
	}

	override suspend fun getStateById(stateId: UUID): StateDto {
		return statesHandler.readByEntityId(stateId)
	}

	override suspend fun createState(state: StateDto): Boolean {
		statesHandler.write(state)
		return true
	}

	override suspend fun editState(state: StateDto) {
		statesHandler.edit(state)
	}
	
	override suspend fun recordLog(log: LogDto) {
		logsHandler.write(log)
	}
	
	override suspend fun getTaskLogs(taskId: UUID): List<LogDto> {
		return logsHandler.readAll().filter { it.entityType == EntityType.TASK && it.entityId == taskId }
	}

	override suspend fun getProjectLogs(projectId: UUID): List<LogDto> {
		return logsHandler.readAll()
			.filter { it.entityType == EntityType.PROJECT && it.entityId == projectId }
	}
}