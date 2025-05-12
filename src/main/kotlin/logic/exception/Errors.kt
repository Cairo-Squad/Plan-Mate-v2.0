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

open class OperationException : Exception()
class WriteException : OperationException()

open class NetworkException : Exception() {
    data class IOException(val errorMsg: String = "No Internet Connection") : NetworkException()
    data class InvalidCollectionName(val errorMsg: String = "Invalid collection name") : NetworkException()
    data class WriteException(val errorMsg: String? = "Api Write Exception") : NetworkException()
    data class ApiException(val errorMsg: String? = "Api UnKnown Exception") : NetworkException()
}

