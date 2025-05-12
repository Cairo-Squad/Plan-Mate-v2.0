package data.repositories

import logic.exception.AdminException
import logic.exception.GeneralException
import logic.exception.UserException

abstract class BaseRepository {
    protected suspend fun <T> wrap(function: suspend () -> T): T {
        return try {
            function()
        } catch (e: Exception) {
            when (e) {
                is UserException -> throw UserException()
                is AdminException -> throw AdminException()
                is GeneralException -> throw GeneralException()
                else-> {
                    println(e)
                    throw Exception("handel")

                }


            }
        }
    }
}