package logic.repositories

import logic.model.Log

interface LogsRepository {
    fun addProjectLog(log: Log)
}