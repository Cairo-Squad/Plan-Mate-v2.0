package data.repositories.mappers

import data.dto.ProjectLogDto
import data.dto.TaskLogDto
import logic.model.ProjectLog
import logic.model.TaskLog

fun TaskLogDto.toTaskLog(): TaskLog {
    return TaskLog(
        taskId = this.taskId,
        userId = this.userId,
        action = this.action,
        time = this.time
    )}
fun TaskLog.toTaskLogDto(): TaskLogDto {
    return TaskLogDto(
        taskId = this.taskId,
        userId = this.userId,
        action = this.action,
        time = this.time
    )}

fun ProjectLogDto.toProjectLog(): ProjectLog {
    return ProjectLog(
        projectId = this.projectId,
        userId = this.userId,
        action = this.action,
        time = this.time,
    )
}
fun ProjectLog.toProjectLogDto(): ProjectLogDto {
    return ProjectLogDto(
        projectId = this.projectId,
        userId = this.userId,
        action = this.action,
        time = this.time,
    )
}