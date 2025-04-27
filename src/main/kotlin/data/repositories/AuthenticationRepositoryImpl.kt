package data.repositories

import data.dataSource.DataSource
import logic.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl (
    private val dataSource: DataSource
): AuthenticationRepository {

}