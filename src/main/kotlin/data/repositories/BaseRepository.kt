package data.repositories

import logic.util.AdminException
import logic.util.GeneralException
import logic.util.UserException

abstract class BaseRepository {
    protected fun <T> wrap(function: () -> T): T {
        return try {
            function()
        } catch (e: Exception) {
            when (e) {
                is UserException -> throw UserException()
                is AdminException -> throw AdminException()
                is GeneralException -> throw GeneralException()
                else -> throw Exception("Unhandled exception")
            }
        }
    }
}