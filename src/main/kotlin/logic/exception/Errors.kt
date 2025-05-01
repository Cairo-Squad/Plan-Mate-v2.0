package logic.exception

class NameNotEmptyException(name:String): Exception(name)

class PasswordNotEmptyException(name:String): Exception(name)

class UnknownException() : Exception("Unknown exception has occurred")

class CsvWriteException() : Exception("Failed to write to CSV file")

class EmptyNameException : Exception("Name cannot be empty")

class EmptyPasswordException : Exception("Password cannot be empty")

class UserNotChangedException : Exception("Updated user matches the original user")
