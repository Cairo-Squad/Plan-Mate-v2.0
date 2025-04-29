package data.repositories.mappers

import data.dto.StateDto
import logic.model.State

fun State.toStateDto():StateDto{
	return StateDto(
		id = this.id,
		title = this.title
	)
}

fun StateDto.toState():State{
	return State(
		id = this.id,
		title = this.title
	)
}
