package data.repositories

import data.dataSource.DataSource
import logic.repositories.StatesRepository

class StatesRepositoryImpl(
    private val dataSource: DataSource
) : StatesRepository {
}