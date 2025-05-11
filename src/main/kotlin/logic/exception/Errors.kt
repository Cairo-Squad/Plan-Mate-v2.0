package logic.exception

open class UserException : Exception()
class InvalidUserCredentialsException : UserException()
class InvalidUserException : UserException()

open class AdminException : Exception()
class UnauthorizedActionException : AdminException()

open class GeneralException : Exception()
class NotFoundException : GeneralException()
class UnknownException : GeneralException()

class EntityNotChangedException : Exception()

class EmptyNameException : IllegalArgumentException()
class EmptyTitleException : IllegalArgumentException()

open class OperationException: Exception()
class WriteException: OperationException()

