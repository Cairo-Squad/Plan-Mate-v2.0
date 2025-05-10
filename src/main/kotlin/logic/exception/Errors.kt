package logic.exception

open class UserException : Exception()
class InvalidUserCredentialsException : UserException()
class UnauthorizedUserActionException : UserException()

open class AdminException : Exception()

open class GeneralException : Exception()
class NotFoundException : GeneralException()

class UnknownException() : Exception("Unknown exception has occurred")

class InvalidUserException : Exception("Mate is not allowed to create project")

class ProjectNotFoundException : Exception("Project not found")

class WriteException : Exception("Failed to write to CSV file")

class EmptyNameException : Exception("Name cannot be empty")

class EmptyTitleException : Exception("Title cannot be empty")

class EntityNotChangedException : Exception("Entity not changed")

class CsvParseException(message: String? = null, cause: Throwable? = null) :
    Exception(message ?: "Failed to parse CSV data", cause)


