package data.dataSource.remoteDataSource.mongo.handler

import java.util.UUID

interface MongoDBHandler<DTO> {
    fun write(entity : DTO) : Pair<Boolean, UUID?>
    fun edit(entity : DTO)
    fun delete(entity : DTO)
    fun readAll() : List<DTO>
    fun readByEntityId(id : UUID) : DTO
}