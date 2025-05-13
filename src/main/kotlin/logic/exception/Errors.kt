package logic.exception

open class UserException : Exception()
class InvalidUserCredentialsException(val errorMsg: String = "Invalid Credentials") : UserException()
class InvalidUserTypeException(val errorMsg: String = "Only Allowed Admins") : UserException()

class NameException(val errorMsg: String = "Not Valid Name") : IllegalArgumentException()
class TitleException(val errorMsg: String = "Not Valid Title") : IllegalArgumentException()

sealed class NetworkException : Exception() {
    data class IOException(val errorMsg: String = "No Internet Connection") : NetworkException()
    data class InvalidCollectionName(val errorMsg: String = "Invalid collection name") : NetworkException()
    data class WriteException(val errorMsg: String? = "Api Write Exception") : NetworkException()
    data class ApiException(val errorMsg: String? = "Api UnKnown Exception") : NetworkException()
}

