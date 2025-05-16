package data.dataSource.remoteDataSource.mongo.handler

import java.util.UUID

interface MongoDBHandler<DTO> {
    fun write(entity: DTO):  DTO
    fun edit(entity: DTO)
    fun delete(entityId: UUID)
    fun readAll(): List<DTO>
    fun readByEntityId(id: UUID): DTO
}