package data.dataSource.remoteDataSource.mongo.handler

import java.util.UUID

interface MongoDBHandler<DTO> {
    fun write(entity: DTO): Boolean
    fun edit(entity: DTO):Boolean
    fun delete(entityId: UUID):Boolean
    fun readAll(): List<DTO>
    fun readByEntityId(id: UUID): DTO

    //overlaoding
}