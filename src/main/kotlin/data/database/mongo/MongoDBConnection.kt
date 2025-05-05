package data.database.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase

object MongoDBConnection {
	private var mongoClient: MongoClient? = null
	private const val DATABASE_NAME = "project_management"
	
	fun getDatabase(connectionString: String = "mongodb://localhost:27017"): MongoDatabase {
		if (mongoClient == null) {
			val settings = MongoClientSettings.builder()
				.applyConnectionString(ConnectionString(connectionString))
				.build()
			mongoClient = MongoClients.create(settings)
		}
		return mongoClient!!.getDatabase(DATABASE_NAME)
	}
	
	fun close() {
		mongoClient?.close()
		mongoClient = null
	}
}