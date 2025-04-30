package data.dataSource

import data.dto.*
import java.util.*

interface DataSource {
    fun createUser(id: UUID, name:String, password:String, type:UserType): UserDto
    fun getAllUsers(): List<UserDto>
    fun getAllProjects(): List<ProjectDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogDto>
}