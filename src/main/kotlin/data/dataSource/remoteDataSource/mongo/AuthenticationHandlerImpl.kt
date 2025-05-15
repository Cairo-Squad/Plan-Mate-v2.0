package data.dataSource.remoteDataSource.mongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Projections
import data.dataSource.util.MongoConstants
import data.dto.UserDto
import logic.model.UserType
import org.bson.Document
import java.util.*

class AuthenticationHandlerImpl(
    private val database: MongoDatabase,
) : AuthenticationHandler {
    companion object {
        const val COLLECTION_NAME = "users"
        var id: UUID? = null
    }

    private val collection: MongoCollection<Document> by lazy {
        database.getCollection(COLLECTION_NAME)
    }

    override suspend fun signUp(userName: String, userPassword: String, userType: UserType): UUID {
        collection.insertOne(createSignUpDocument(userName, userPassword, UserType.ADMIN))
        return id!! // this line will be executed if the above line didn't throw an exception
    }

    override suspend fun login(userName: String, userPassword: String): UUID? {
        val filter = Filters.and(
            Filters.eq(MongoConstants.USER_NAME, userName),
            Filters.eq(MongoConstants.USER_PASSWORD, userPassword)
        )
        val projection = Projections.include(MongoConstants.USER_ID)
        val document: Document = collection
            .find(filter).projection(projection)
            .firstOrNull() ?: return null
        return document.get(MongoConstants.USER_ID, UUID::class.java)
    }

    private fun createSignUpDocument(userName: String, userPassword: String, userType: UserType): Document {
        id = UUID.randomUUID()
        return Document()
            .append(MongoConstants.USER_ID, id)
            .append(MongoConstants.USER_NAME, userName)
            .append(MongoConstants.USER_PASSWORD, userPassword)
            .append(MongoConstants.USER_TYPE, userType.name)
    }
}