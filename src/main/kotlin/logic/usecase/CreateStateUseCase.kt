package logic.usecase

import data.repositories.mappers.toStateDto
import data.repositories.mappers.toUserDto
import logic.model.State
import logic.model.User
import logic.repositories.StatesRepository

class CreateStateUseCase(
	private val stateRepo: StatesRepository
) {
	fun createState(state: State, user: User): Boolean {
		return try {
			stateRepo.createState(state.toStateDto(), user.toUserDto())
		} catch (e: Exception) {
			return false
		}
	}
}