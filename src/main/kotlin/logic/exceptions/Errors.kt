package logic.exceptions

import java.io.IOException
import java.util.*


class NameNotEmptyException(name:String): Exception(name)

class PasswordNotEmptyException(name:String): Exception(name)

class UnknownException() : Exception("unknown exception has occurred")

class CsvWriteException() : Exception("Failed to write to CSV file")

class EntityNotFoundException() : Exception("Entity not found.")

class CsvReadException() : IOException("Failed to read from this CSV file")
