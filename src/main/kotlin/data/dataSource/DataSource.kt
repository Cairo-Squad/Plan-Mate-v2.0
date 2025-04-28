package data.dataSource

import data.dto.*

interface DataSource {
    fun createUser(name:String, password: String, userType: UserType)
    fun getAllUsers(): List<UserDto>
    fun getAllProjects(): List<ProjectDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogEntityDto>
}