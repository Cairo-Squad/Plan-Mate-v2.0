package data.dataSource

import data.database.FileHandler
import data.dto.*
import java.util.UUID

class CsvDataSource(
    private val logsCsvHandler: FileHandler<LogDto>,
    private val projectsCsvHandler: FileHandler<ProjectDto>,
    private val statesCsvHandler: FileHandler<StateDto>,
    private val tasksCsvHandler: FileHandler<TaskDto>,
    private val usersCsvHandler: FileHandler<UserDto>
) : DataSource {
    override fun getAllUsers(): List<UserDto> {
        TODO("Not yet implemented")
    }

    override fun getAllProjects(): List<ProjectDto> {
        TODO("Not yet implemented")
    }

    override fun getAllTasks(): List<TaskDto> {
        TODO("Not yet implemented")
    }

    override fun getAllAuditRecords(): List<LogDto> {
        TODO("Not yet implemented")
    }

    override fun recordLog(log: LogDto) {
        logsCsvHandler.write(log)
    }

    override fun getTaskLogs(taskId: UUID): List<LogDto> {
        return logsCsvHandler.readAll().filter { it.entityType == EntityType.TASK && it.entityId == taskId }
    }

    override fun createTask(task: TaskDto): Result<Unit> {
        return try {
            tasksCsvHandler.write(task)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun deleteTask(task: TaskDto) {
        tasksCsvHandler.delete(task)
    }
    override fun getTaskById(taskID: UUID): TaskDto {

        val task = tasksCsvHandler.readAll().find { it.id == taskID }
        return task!!

    }

    override fun getStateById(stateId: UUID): StateDto {
        val stateDto = statesCsvHandler.readAll().find { it.id == stateId }
        return stateDto!!
    }
}