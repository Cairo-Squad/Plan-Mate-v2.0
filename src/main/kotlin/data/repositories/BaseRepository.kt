package data.repositories

import com.mongodb.MongoException
import com.mongodb.MongoWriteException
import logic.exception.AdminException
import logic.exception.GeneralException
import logic.exception.NetworkException
import logic.exception.UserException
import java.io.IOException

abstract class BaseRepository {
    protected suspend fun <T> wrap(function: suspend () -> T): T {
        return try {
            function()
        } catch (e: Exception) {
            when (e) {
                is UserException -> throw UserException()
                is AdminException -> throw AdminException()
                is GeneralException -> throw GeneralException()
                is IllegalArgumentException -> throw NetworkException.InvalidCollectionName()
                is IOException -> throw NetworkException.IOException()
                is MongoWriteException -> throw NetworkException.WriteException(e.message)
                is MongoException -> throw NetworkException.ApiException(e.message)
                else -> throw Exception("Unhandled exception")
            }
        }
    }
}