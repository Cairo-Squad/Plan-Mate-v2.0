package data.dataSource.localDataSource.file.handler

interface FileHandler<DTO> {
    fun write(entity: DTO): Boolean
    fun edit(entity: DTO)
    fun delete(entity: DTO)
    fun readAll(): List<DTO>

    //overlaoding
    fun edit(entity: DTO, ayhaga: Boolean = true): Boolean
    fun delete(entity: DTO, ayhaga: Boolean = true): Boolean
}