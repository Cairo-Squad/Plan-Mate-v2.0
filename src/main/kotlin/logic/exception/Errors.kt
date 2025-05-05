package logic.exception

import java.io.IOException

class UnknownException() : Exception("Unknown exception has occurred")

class CsvWriteException() : Exception("Failed to write to CSV file")

class EmptyNameException : Exception("Name cannot be empty")

class EmptyPasswordException : Exception("Password cannot be empty")

class DtoNotFoundException : Exception("Not Found DTO")

class EntityNotFoundException() : Exception("Entity not found.")

class CsvReadException() : IOException("Failed to read from this CSV file")

class EntityNotChangedException() : Exception("Entity not changed")

class CsvParseException(message: String? = null, cause: Throwable? = null) :
    Exception(message ?: "Failed to parse CSV data", cause)

