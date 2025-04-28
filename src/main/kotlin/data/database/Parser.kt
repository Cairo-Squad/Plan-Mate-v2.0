package data.database

interface Parser {

    fun <T> write(entity: T)
    fun <T> read(): T

}