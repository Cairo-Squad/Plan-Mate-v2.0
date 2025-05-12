package data.dataSource.remoteDataSource.mongo.handler

import java.util.UUID

interface MongoDBHandler<DTO> {
    fun write(entity: DTO): Boolean
    fun edit(entity: DTO)
    fun delete(entity: DTO)
    fun readAll(): List<DTO>
    fun readByEntityId(id: UUID): DTO

    //overlaoding
    fun edit(entity: DTO, ayhaga: Boolean = true): Boolean
    fun delete(entity: DTO, ayhaga: Boolean = true): Boolean
}