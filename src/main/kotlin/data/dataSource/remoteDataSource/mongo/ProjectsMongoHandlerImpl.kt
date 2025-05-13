package data.dataSource.remoteDataSource.mongo

import com.mongodb.client.MongoDatabase
import data.dataSource.remoteDataSource.mongo.handler.MongoDBHandlerImpl
import data.dataSource.util.MongoConstants
import data.dto.ProjectDto
import org.bson.Document
import java.util.*

class ProjectsMongoHandlerImpl(
	database: MongoDatabase
) : MongoDBHandlerImpl<ProjectDto>(
	database = database,
	collectionName = "projects",
	getDtoId = { it.id?: UUID.randomUUID() }
) {
	override fun convertDtoToDocument(entity: ProjectDto): Document {
		val id = getDtoId(entity)
		return Document()
			.append(MongoConstants.ID, id.toString())
			.append(MongoConstants.TITLE, entity.title)
			.append(MongoConstants.DESCRIPTION, entity.description)
			.append(MongoConstants.CREATED_BY, entity.createdBy.toString())
			.append(MongoConstants.TASKS, entity.taskIds.map { it.toString() })
			.append(MongoConstants.STATE_ID, entity.stateId.toString())
	}
	
	override fun convertDocumentToDto(document: Document): ProjectDto {
		val tasksList = document.getList(MongoConstants.TASKS, String::class.java) ?: emptyList()
		
		return ProjectDto(
			id = UUID.fromString(document.getString(MongoConstants.ID)),
			title = document.getString(MongoConstants.TITLE),
			description = document.getString(MongoConstants.DESCRIPTION),
			createdBy = UUID.fromString(document.getString(MongoConstants.CREATED_BY)),
			taskIds = tasksList.map { UUID.fromString(it) },
			stateId = UUID.fromString(document.getString(MongoConstants.STATE_ID))
		)
	}
}