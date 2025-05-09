package data.dataSource.remoteDataSource.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import data.dataSource.util.MongoConstants

object MongoDBConnection {
	private var mongoClient: MongoClient? = null
	private const val DATABASE_NAME = MongoConstants.DATABASE_NAME
	
	fun getDatabase(connectionString: String = MongoConstants.MONGO_DB_CONNECT_STRING): MongoDatabase {
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