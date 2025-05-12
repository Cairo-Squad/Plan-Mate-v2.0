package data.dataSource.remoteDataSource.mongo.handler

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import logic.exception.NotFoundException
import org.bson.Document
import java.util.*

abstract class MongoDBHandlerImpl<DTO>(
    protected val database: MongoDatabase,
    protected val collectionName: String,
    protected val getDtoId: (DTO) -> UUID
) : MongoDBHandler<DTO> {

    private val collection: MongoCollection<Document> by lazy {
        database.getCollection(collectionName)
    }

    init {
        if (!collectionExists()) {
            database.createCollection(collectionName)
        }
    }

    abstract fun convertDtoToDocument(entity: DTO): Document
    abstract fun convertDocumentToDto(document: Document): DTO

    private fun collectionExists(): Boolean {
        return database.listCollectionNames().contains(collectionName)
    }

    override fun write(entity: DTO): Boolean {
        val collectionSizeBeforeInsert = collection.countDocuments()
        val document = convertDtoToDocument(entity)
        collection.insertOne(document)
        val collectionSizeAfterInsert = collection.countDocuments()
        
        return collectionSizeAfterInsert > collectionSizeBeforeInsert
    }

    override fun edit(entity: DTO) {
        val entityId = getDtoId(entity)
        val document = convertDtoToDocument(entity)
        val result = collection.replaceOne(Filters.eq("_id", entityId.toString()), document)
        if (result.matchedCount == 0L) {
            throw NotFoundException()
        }
    }

    override fun delete(entity: DTO) {
        val entityId = getDtoId(entity)
        val result = collection.deleteOne(Filters.eq("_id", entityId.toString()))
        if (result.deletedCount == 0L) {
            throw NotFoundException()
        }
    }

    override fun readAll(): List<DTO> {
        return collection.find().asSequence()
            .map { convertDocumentToDto(it) }
            .toList()

    }

    override fun readByEntityId(id: UUID): DTO {
        val document = collection.find(Filters.eq("_id", id.toString())).first()
            ?: throw NotFoundException()
        return convertDocumentToDto(document)
    }
    override fun edit(entity: DTO , ayhaga: Boolean) :Boolean{
        val entityId = getDtoId(entity)
        val document = convertDtoToDocument(entity)
        val result = collection.replaceOne(Filters.eq("_id", entityId.toString()), document)
        if (result.matchedCount == 0L) {
            return false
        }
        return true
    }

    override fun delete(entity: DTO ,ayhaga: Boolean):Boolean {
        val entityId = getDtoId(entity)
        val result = collection.deleteOne(Filters.eq("_id", entityId.toString()))
        if (result.deletedCount == 0L) {
            return false
        }
        return true
    }
}
