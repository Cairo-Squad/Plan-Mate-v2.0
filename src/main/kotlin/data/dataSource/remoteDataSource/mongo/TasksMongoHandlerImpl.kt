package data.dataSource.remoteDataSource.mongo

import com.mongodb.client.MongoDatabase
import data.dataSource.remoteDataSource.mongo.handler.MongoDBHandlerImpl
import data.dataSource.util.MongoConstants
import data.dto.TaskDto
import org.bson.Document
import java.util.*

class TasksMongoHandlerImpl(
	database: MongoDatabase
) : MongoDBHandlerImpl<TaskDto>(
	database = database,
	collectionName = "tasks",
	getDtoId = { it.id?: UUID.randomUUID() }
) {
	override fun convertDtoToDocument(entity: TaskDto): Document {
		val id = getDtoId(entity)
		return Document()
			.append(MongoConstants.TASK_ID, id)
			.append(MongoConstants.TASK_TITLE, entity.title)
			.append(MongoConstants.TASK_DESCRIPTION, entity.description)
			.append(MongoConstants.TASK_STATE_ID, entity.stateId)
			.append(MongoConstants.TASK_PROJECT_ID, entity.projectId)
	}
	
	override fun convertDocumentToDto(document: Document): TaskDto {
		return TaskDto(
			id = document.get(MongoConstants.TASK_ID,UUID::class.java),
			title = document.getString(MongoConstants.TASK_TITLE),
			description = document.getString(MongoConstants.TASK_DESCRIPTION),
			stateId = document.get(MongoConstants.TASK_STATE_ID,UUID::class.java),
			projectId = document.get(MongoConstants.TASK_PROJECT_ID,UUID::class.java)
		)
	}
}