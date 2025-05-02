package logic.usecase

import logic.model.State
import logic.model.User
import logic.repositories.StatesRepository

class CreateStateUseCase(
	private val stateRepo: StatesRepository
) {
	fun createState(state:State, user: User):Boolean{
		return false
	}
}