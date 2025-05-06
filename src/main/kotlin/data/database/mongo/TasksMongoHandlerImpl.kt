package data.database.mongo

import com.mongodb.client.MongoDatabase
import data.database.util.MongoConstants
import data.dto.TaskDto
import org.bson.Document
import java.util.*

class TasksMongoHandlerImpl(
	database: MongoDatabase
) : MongoDBHandlerImpl<TaskDto>(
	database = database,
	collectionName = "tasks",
	getDtoId = { it.id }
) {
	override fun convertDtoToDocument(entity: TaskDto): Document {
		return Document()
			.append(MongoConstants.TASK_ID, entity.id.toString())
			.append(MongoConstants.TASK_TITLE, entity.title)
			.append(MongoConstants.TASK_DESCRIPTION, entity.description)
			.append(MongoConstants.TASK_STATE_ID, entity.stateId.toString())
			.append(MongoConstants.TASK_PROJECT_ID, entity.projectId.toString())
	}
	
	override fun convertDocumentToDto(document: Document): TaskDto {
		return TaskDto(
			id = UUID.fromString(document.getString(MongoConstants.TASK_ID)),
			title = document.getString(MongoConstants.TASK_TITLE),
			description = document.getString(MongoConstants.TASK_DESCRIPTION),
			stateId = UUID.fromString(document.getString(MongoConstants.TASK_STATE_ID)),
			projectId = UUID.fromString(document.getString(MongoConstants.TASK_PROJECT_ID))
		)
	}
}