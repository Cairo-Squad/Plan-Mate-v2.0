package data.database.mongo

import java.util.UUID

interface MongoDBHandler<DTO> {
	suspend fun write(entity: DTO)
	suspend fun edit(entity: DTO)
	suspend fun delete(entity: DTO)
	suspend fun readAll():List<DTO>
	suspend fun readByEntityId(id:UUID):DTO
}