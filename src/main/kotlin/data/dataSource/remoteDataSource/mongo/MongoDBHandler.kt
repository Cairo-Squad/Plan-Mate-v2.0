package data.dataSource.remoteDataSource.mongo

import java.util.UUID

interface MongoDBHandler<DTO> {
    fun write(entity : DTO) : Boolean
    fun edit(entity : DTO)
    fun delete(entity : DTO)
    fun readAll() : List<DTO>
    fun readByEntityId(id : UUID) : DTO
}