package logic.exception

sealed class PlanMateException : Exception() {

    sealed class ValidationException : Exception() {
        data class InvalidCredentialsException(val errorMsg: String = "Invalid Credentials") :
            ValidationException()

        data class InvalidUserTypeException(val errorMsg: String = "Only Allowed Admins") : ValidationException()
        data class NameException(val errorMsg: String = "Not Valid Name") : ValidationException()
        data class TitleException(val errorMsg: String = "Not Valid Title") : ValidationException()
    }


    sealed class NetworkException : Exception() {
        data class IOException(val errorMsg: String = "No Internet Connection") : NetworkException()
        data class InvalidCollectionName(val errorMsg: String = "Invalid collection name") : NetworkException()
        data class WriteException(val errorMsg: String? = "Api Write Exception") : NetworkException()
        data class ApiException(val errorMsg: String? = "Api UnKnown Exception") : NetworkException()
    }
}

