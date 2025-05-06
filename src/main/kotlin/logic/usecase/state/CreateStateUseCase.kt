package logic.usecase.state

import data.repositories.mappers.toStateDto
import data.repositories.mappers.toUserDto
import logic.model.State
import logic.model.User
import logic.repositories.LogsRepository
import logic.repositories.StatesRepository

class CreateStateUseCase(
	private val stateRepo: StatesRepository
) {
	fun createState(state: State): Boolean {
		return try {
			stateRepo.createState(state.toStateDto())
		} catch (e: Exception) {
			return false
		}
	}
}