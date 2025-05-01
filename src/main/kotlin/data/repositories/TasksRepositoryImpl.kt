package data.repositories

import data.dataSource.DataSource
import data.dto.StateDto
import data.dto.TaskDto
import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import data.repositories.mappers.toTaskDto
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import java.util.UUID

class TasksRepositoryImpl(
    private val dataSource: DataSource
) : TasksRepository {

    override fun getTaskById(taskID: UUID): Task {

        val taskDto = dataSource.getTaskById(taskID)
        val taskState = getStateById(taskDto.stateId)
        return taskDto.toTask(taskState.toState())
    }

    private fun getStateById(stateId: UUID): StateDto {
        return dataSource.getStateById(stateId)
    }
}