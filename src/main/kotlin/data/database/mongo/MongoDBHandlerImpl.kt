package data.database.mongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import logic.exception.DtoNotFoundException
import logic.exception.EntityNotFoundException
import logic.exception.WriteException
import org.bson.Document
import java.util.*

abstract class MongoDBHandlerImpl<DTO>(
	protected val database: MongoDatabase,
	protected val collectionName: String,
	private val getDtoId: (DTO) -> UUID
) : MongoDBHandler<DTO> {
	
	private val collection: MongoCollection<Document> by lazy {
		database.getCollection(collectionName)
	}
	
	init {
		if (!collectionExists()) {
			database.createCollection(collectionName)
		}
	}
	
	private fun collectionExists(): Boolean {
		return database.listCollectionNames().contains(collectionName)
	}
	
	override suspend fun write(entity: DTO) {
		try {
			val document = convertDtoToDocument(entity)
			collection.insertOne(document)
		} catch (e: Exception) {
			throw WriteException()
		}
	}
	
	override suspend fun edit(entity: DTO) {
		try {
			val entityId = getDtoId(entity)
			val document = convertDtoToDocument(entity)
			val result = collection.replaceOne(Filters.eq("_id", entityId.toString()), document)
			if (result.matchedCount == 0L) {
				throw DtoNotFoundException()
			}
		} catch (e: DtoNotFoundException) {
			throw e
		} catch (e: Exception) {
			throw WriteException()
		}
	}
	
	override suspend fun delete(entity: DTO) {
		try {
			val entityId = getDtoId(entity)
			val result = collection.deleteOne(Filters.eq("_id", entityId.toString()))
			if (result.deletedCount == 0L) {
				throw EntityNotFoundException()
			}
		} catch (e: EntityNotFoundException) {
			throw e
		} catch (e: Exception) {
			throw WriteException()
		}
	}
	
	override suspend fun readAll(): List<DTO> {
		return try {
			collection.find()
				.map { convertDocumentToDto(it) }
				.toList()
		} catch (e: Exception) {
			throw WriteException()
		}
	}
	
	override suspend fun readByEntityId(id: UUID): DTO {
		try {
			val document = collection.find(Filters.eq("_id", id.toString())).first()
				?: throw DtoNotFoundException()
			return convertDocumentToDto(document)
		} catch (e: DtoNotFoundException) {
			throw e
		} catch (e: Exception) {
			throw WriteException()
		}
	}
	
	abstract fun convertDtoToDocument(entity: DTO): Document
	abstract fun convertDocumentToDto(document: Document): DTO
}
