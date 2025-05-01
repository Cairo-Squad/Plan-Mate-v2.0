package logic.exception

class NameNotEmptyException(name:String): Exception(name)

class PasswordNotEmptyException(name:String): Exception(name)

class UnknownException() : Exception("unknown exception has occurred")

class CsvWriteException() : Exception("Failed to write to CSV file")