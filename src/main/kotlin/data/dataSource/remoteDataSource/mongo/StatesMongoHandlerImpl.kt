package data.dataSource.remoteDataSource.mongo

import com.mongodb.client.MongoDatabase
import data.dataSource.remoteDataSource.mongo.handler.MongoDBHandlerImpl
import data.dataSource.util.MongoConstants
import data.dto.StateDto
import org.bson.Document
import java.util.*

class StatesMongoHandlerImpl(
    database: MongoDatabase
) : MongoDBHandlerImpl<StateDto>(
    database = database,
    collectionName = "states",
    getDtoId = { it.id ?: UUID.randomUUID() }
) {
    override fun convertDtoToDocument(entity: StateDto): Document {
        val id = getDtoId(entity)
        return Document()
            .append(MongoConstants.ID, id).also { println("State id = $id") }
            .append(MongoConstants.STATE_TITLE, entity.title)
    }


    override fun convertDocumentToDto(document: Document): StateDto {
        println("Document to Convert $document")
        return StateDto(
            id = document.get(MongoConstants.ID,UUID::class.java),
            title = document.getString(MongoConstants.STATE_TITLE)
        )
    }
}