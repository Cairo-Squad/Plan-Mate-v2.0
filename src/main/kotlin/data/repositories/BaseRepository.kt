package data.repositories

abstract class BaseRepository {
    protected fun <T> tryToExecute(function: () -> T): T {
        return try {
            function()
        } catch (e: Exception) {
            throw e
        }
    }
}