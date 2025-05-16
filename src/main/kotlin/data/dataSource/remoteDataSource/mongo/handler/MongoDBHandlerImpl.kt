package data.dataSource.remoteDataSource.mongo.handler

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Projections
import com.mongodb.client.result.InsertOneResult
import data.customException.PlanMateException
import data.dataSource.util.MongoConstants
import org.bson.Document
import java.util.*

abstract class MongoDBHandlerImpl<DTO>(
    protected val database : MongoDatabase,
    protected val collectionName : String,
    protected val getDtoId : (DTO) -> UUID
) : MongoDBHandler<DTO> {

    private val collection : MongoCollection<Document> by lazy {
        database.getCollection(collectionName)
    }

    init {
        if (!collectionExists()) {
            database.createCollection(collectionName)
        }
    }

    abstract fun convertDtoToDocument(entity : DTO) : Document
    abstract fun convertDocumentToDto(document : Document) : DTO

    private fun collectionExists() : Boolean {
        return database.listCollectionNames().contains(collectionName)
    }

    override fun write(entity : DTO) : DTO {
        val document = convertDtoToDocument(entity)
        collection.insertOne(document)
        return convertDocumentToDto(document)
    }

    override fun edit(entity : DTO) {
        val entityId = getDtoId(entity)
        val document = convertDtoToDocument(entity)
        collection.replaceOne(Filters.eq("_id", entityId.toString()), document)
    }

    override fun delete(entityId : UUID) {
        collection.deleteOne(Filters.eq("_id", entityId.toString()))
    }

    override fun readAll() : List<DTO> {
        return collection.find()
            .map { convertDocumentToDto(it) }
            .toList()
    }

    override fun readByEntityId(id : UUID) : DTO {
        val filter = Filters.eq(MongoConstants.USER_ID, id)
        val projection = Projections.exclude(MongoConstants.USER_PASSWORD)
        val document =
            collection.find(filter).projection(projection).first()
                ?: throw PlanMateException.NetworkException.DataNotFoundException("Couldn't find user in mongoDB")
        return convertDocumentToDto(document)
    }
}