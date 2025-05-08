package logic.util

open class UserException : Exception()
class InvalidUserCredentialsException : UserException()
class UnauthorizedUserActionException : UserException()

open class AdminException : Exception()
class AdminAccessDeniedException : AdminException()

open class GeneralException : Exception()
class NotFoundException : GeneralException()
class AlreadyExistsException : GeneralException()
