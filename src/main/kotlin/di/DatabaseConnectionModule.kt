package di

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import data.dataSource.util.MongoConstants
import org.bson.UuidRepresentation
import org.koin.dsl.module

val mongoDatabaseModule = module {
    single<MongoClient> {
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(MongoConstants.MONGO_DB_CONNECT_STRING))
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .build()
        val client = MongoClients.create(settings)

        Runtime.getRuntime().addShutdownHook(Thread {
            client.close()
        })

        client
    }

    single<MongoDatabase> {
        val mongoClient = get<MongoClient>()
        mongoClient.getDatabase(MongoConstants.DATABASE_NAME)
    }
}