package data.database

interface FileHandler<DTO> {
    fun write(entity: DTO)
    fun readAll(): List<DTO>
    fun edit(entity: DTO):Boolean
    fun delete(entity: DTO)
}