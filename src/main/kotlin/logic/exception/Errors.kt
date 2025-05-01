package logic.exception

import java.io.IOException

class UnknownException() : Exception("Unknown exception has occurred")

class CsvWriteException() : Exception("Failed to write to CSV file")

class EmptyNameException : Exception("Name cannot be empty")

class EmptyPasswordException : Exception("Password cannot be empty")

class DtoNotFoundException : Exception("Not Found DTO")

class EntityNotFoundException() : Exception("Entity not found.")

class CsvReadException() : IOException("Failed to read from this CSV file")

