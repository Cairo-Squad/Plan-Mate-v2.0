package data.dataSource.remoteDataSource.mongo.handler

import java.util.UUID

interface MongoDBHandler<DTO> {
    fun write(entity : DTO) : Boolean
    fun edit(entity : DTO) : Boolean
    fun delete(entityId : UUID) : Boolean
    fun readAll() : List<DTO>
    fun readByEntityId(id : UUID) : DTO

    //overloading
    fun write(entity : DTO, isFakeParam : Boolean = true) : DTO
    fun edit(entity : DTO, isFakeParam : Boolean = true)
    fun delete(entityId : UUID, isFakeParam : Boolean = true)
}