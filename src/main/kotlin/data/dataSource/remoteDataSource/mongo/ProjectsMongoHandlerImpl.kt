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
    getDtoId = { it.id ?: UUID.randomUUID() }
) {
    override fun convertDtoToDocument(entity: ProjectDto): Document {
        val id = getDtoId(entity)
        return Document()
            .append(MongoConstants.ID, id)
            .append(MongoConstants.TITLE, entity.title)
            .append(MongoConstants.DESCRIPTION, entity.description)
            .append(MongoConstants.CREATED_BY, entity.createdBy)
            .append(MongoConstants.STATE_ID, entity.stateId)
    }

    override fun convertDocumentToDto(document: Document): ProjectDto {

        return ProjectDto(
            id = document.get(MongoConstants.ID, UUID::class.java),
            title = document.getString(MongoConstants.TITLE),
            description = document.getString(MongoConstants.DESCRIPTION),
            createdBy = document.get(MongoConstants.CREATED_BY,UUID::class.java),
            stateId = document.get(MongoConstants.STATE_ID,UUID::class.java)
        )
    }
}