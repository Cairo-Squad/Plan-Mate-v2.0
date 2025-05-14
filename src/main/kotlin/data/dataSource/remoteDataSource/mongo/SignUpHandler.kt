package data.dataSource.remoteDataSource.mongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import data.dataSource.util.MongoConstants
import logic.model.UserType
import org.bson.Document
import java.util.*

class SignUpHandler(
    private val database: MongoDatabase,
) {
    companion object {
        const val COLLECTION_NAME = "users"
    }

    private val collection: MongoCollection<Document> by lazy {
        database.getCollection(COLLECTION_NAME)
    }

    private fun createSignUpDocument(signUpRequest: SignUpRequest): Document {
        return Document()
            .append(MongoConstants.USER_ID, signUpRequest.userId.toString())
            .append(MongoConstants.USER_NAME, signUpRequest.userName)
            .append(MongoConstants.USER_PASSWORD, signUpRequest.userPassword)
            .append(MongoConstants.USER_TYPE, signUpRequest.userType.name)
    }

    fun createNewUser(signUpRequest: SignUpRequest): UUID {
        collection.insertOne(createSignUpDocument(signUpRequest))
        return signUpRequest.userId // this line will be executed if the above line didn't throw an exception
    }

    data class SignUpRequest(
        val userId: UUID = UUID.randomUUID(),
        val userName: String,
        val userPassword: String,
        val userType: UserType,
    )
}