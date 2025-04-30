package data.database

interface FileHandler<DTO> {
    fun write(entity: DTO)
    fun edit(entity: DTO)
    fun delete(entity: DTO)
}