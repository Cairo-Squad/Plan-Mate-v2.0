package data.customException

sealed class PlanMateException : Exception() {

    data class NotYetImplementedException(val errorMsg: String = "This Feature is not yet implemented" ):PlanMateException()
    sealed class ValidationException : PlanMateException() {
        data class InvalidCredentialsException(val errorMsg: String = "Invalid Credentials") :
            ValidationException()
        
        data class InvalidUserTypeException(val errorMsg: String = "Only Allowed Admins") : ValidationException()
        data class NameException(val errorMsg: String = "Not Valid Name") : ValidationException()
        data class TitleException(val errorMsg: String = "Not Valid Title") : ValidationException()
    }
    
    sealed class LocalDBException : PlanMateException() {
        data class DataNotFoundException(val errorMsg: String = "Data Not Found In Local Data Source") : LocalDBException()
        data class UnKnownException(val errorMsg: String = "Unknown Exception While Working With Local Database"):
            LocalDBException()
    }
    
    sealed class NetworkException : PlanMateException() {
        data class UnKnownException(val errorMsg: String = "Unknown Exception While Working With Remote Database"):
            NetworkException()
        data class DataNotFoundException(val errorMsg: String = "Data Not Found In Remote Data Source") : NetworkException()
        data class IOException(val errorMsg: String = "No Internet Connection") : NetworkException()
        data class InvalidCollectionName(val errorMsg: String = "Invalid collection name") : NetworkException()
        data class WriteException(val errorMsg: String? = "Api Write Exception") : NetworkException()
        data class ApiException(val errorMsg: String? = "Api UnKnown Exception") : NetworkException()
        data class ParsingException(val errorMsg: String?) : NetworkException()
    }

}

