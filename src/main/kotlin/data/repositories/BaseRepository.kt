package data.repositories

import com.mongodb.MongoException
import com.mongodb.MongoWriteException
import data.customException.PlanMateException
import java.io.IOException

abstract class BaseRepository {
    protected suspend fun <T> wrap(function: suspend () -> T): T {
        return try {
            function()
        } catch (e: Exception) {
            when (e) {
                is PlanMateException -> throw e
                is IllegalArgumentException -> throw PlanMateException.NetworkException.InvalidCollectionName()
                is IOException -> throw PlanMateException.NetworkException.IOException()
                is MongoWriteException -> throw PlanMateException.NetworkException.WriteException(e.message)
                is MongoException -> throw PlanMateException.NetworkException.ApiException(e.message)
                else -> throw Exception("Unhandled exception")
            }
        }
    }
}