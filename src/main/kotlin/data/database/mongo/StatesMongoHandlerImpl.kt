package data.database.mongo

import com.mongodb.client.MongoDatabase
import data.database.util.MongoConstants
import data.dto.StateDto
import org.bson.Document
import java.util.*

class StatesMongoHandlerImpl(
	database: MongoDatabase
) : MongoDBHandlerImpl<StateDto>(
	database = database,
	collectionName = "states",
	getDtoId = { it.id }
) {
	override fun convertDtoToDocument(entity: StateDto): Document {
		return Document()
			.append(MongoConstants.ID, entity.id.toString())
			.append(MongoConstants.STATE_TITLE, entity.title)
	}
	
	override fun convertDocumentToDto(document: Document): StateDto {
		return StateDto(
			id = UUID.fromString(document.getString(MongoConstants.ID)),
			title = document.getString(MongoConstants.STATE_TITLE)
		)
	}
}