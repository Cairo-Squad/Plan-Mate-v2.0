package data.dataSource.util

object CsvConstants {
    const val USERS_CSV_FILE_PATH = "users.csv"
    const val PROJECTS_CSV_FILE_PATH = "projects.csv"
    const val TASKS_CSV_FILE_PATH = "tasks.csv"
    const val STATES_CSV_FILE_PATH = "states.csv"
    const val LOGS_CSV_FILE_PATH = "logs.csv"

    val USERS_CSV_FILE_HEADERS = listOf("id", "name", "password", "type")
    val PROJECTS_CSV_FILE_HEADERS = listOf("id", "title", "description", "createdBy", "tasks", "stateId")
    val TASKS_CSV_FILE_HEADERS = listOf("id", "title", "description", "stateId", "projectId")
    val STATES_CSV_FILE_HEADERS = listOf("id", "title")
    val LOGS_CSV_FILE_HEADERS = listOf("id", "entityId", "entityTitle", "entityType", "dateTime", "userId", "userAction")
}