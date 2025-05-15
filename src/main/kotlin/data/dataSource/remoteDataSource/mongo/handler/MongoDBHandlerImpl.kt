package data.dataSource.remoteDataSource.mongo.handler

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.result.InsertOneResult
import data.customException.PlanMateException
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
        val document = convertDtoToDocument(entity)
        val result: InsertOneResult = collection.insertOne(document)
        return result.wasAcknowledged()
    }

    override fun write(entity: DTO, fakeParam: String): DTO {
        val document = convertDtoToDocument(entity)
        collection.insertOne(document)
        return convertDocumentToDto(document)
    }

    override fun edit(entity: DTO): Boolean {
        val entityId = getDtoId(entity)
        val document = convertDtoToDocument(entity)
        val result = collection.replaceOne(Filters.eq("_id", entityId.toString()), document)
        return result.wasAcknowledged()
    }

    override fun delete(entityId: UUID): Boolean {
        val result = collection.deleteOne(Filters.eq("_id", entityId.toString()))
        return result.wasAcknowledged()
    }

    override fun edit(entity: DTO, fakeParam: String) {
        val entityId = getDtoId(entity)
        val document = convertDtoToDocument(entity)
        collection.replaceOne(Filters.eq("_id", entityId.toString()), document)
    }

    override fun delete(entityId: UUID, fakeParam: String) {
        collection.deleteOne(Filters.eq("_id", entityId.toString()))
    }

    override fun readAll(): List<DTO> {
        return collection.find()
            .map { convertDocumentToDto(it) }
            .toList()
    }

    override fun readByEntityId(id: UUID): DTO {
        val document = collection.find(Filters.eq("_id", id.toString())).first()
            ?: throw PlanMateException.NetworkException.DataNotFoundException()
        return convertDocumentToDto(document)
    }
}