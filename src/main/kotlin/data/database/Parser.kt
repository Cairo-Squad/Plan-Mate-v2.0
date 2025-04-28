package data.database

interface Parser<DTO> {

    fun write(entity: DTO)
    fun read(): DTO

}