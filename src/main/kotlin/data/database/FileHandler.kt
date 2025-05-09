package data.database

interface FileHandler<DTO> {
    fun write(entity : DTO) : Boolean
    fun edit(entity : DTO)
    fun delete(entity : DTO)
    fun readAll() : List<DTO>
}