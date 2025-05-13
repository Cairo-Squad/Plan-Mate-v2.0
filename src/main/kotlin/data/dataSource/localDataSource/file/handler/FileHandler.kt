package data.dataSource.localDataSource.file.handler

interface FileHandler<DTO> {
    fun write(entity: DTO): Boolean
    fun edit(entity: DTO): Boolean
    fun delete(entity: DTO): Boolean
    fun readAll(): List<DTO>
}