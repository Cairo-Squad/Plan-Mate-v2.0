package logic.util

open class UserException : Exception()
class InvalidUserCredentialsException : UserException()
class UnauthorizedUserActionException : UserException()

open class AdminException : Exception()
class AdminAccessDeniedException : AdminException()

open class GeneralException : Exception()
class NotFoundException : GeneralException()
class AlreadyExistsException : GeneralException()

sealed interface ErrorHandler {
    object InvalidCredentials : ErrorHandler
    object UnauthorizedAction : ErrorHandler

    object AdminAccessDenied : ErrorHandler

    object NotFound : ErrorHandler
    object AlreadyExists : ErrorHandler
}

fun handelUserException(
    exception: UserException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidUserCredentialsException -> onError(ErrorHandler.InvalidCredentials)
        is UnauthorizedUserActionException -> onError(ErrorHandler.UnauthorizedAction)
    }
}

fun handelAdminException(
    exception: AdminException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is AdminAccessDeniedException -> onError(ErrorHandler.AdminAccessDenied)
    }
}

fun handelGeneralException(
    exception: GeneralException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is NotFoundException -> onError(ErrorHandler.NotFound)
        is AlreadyExistsException -> onError(ErrorHandler.AlreadyExists)
    }
}
