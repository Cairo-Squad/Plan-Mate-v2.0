package data.database

import java.util.UUID

interface FileHandler<DTO> {
    fun write(entity: DTO)
    fun readAll(): List<DTO>
    fun edit(entity: DTO)
    fun delete(entity: DTO)
    fun updateValue(id:UUID, newValue:String, type:AttributeToBeChanged)
}