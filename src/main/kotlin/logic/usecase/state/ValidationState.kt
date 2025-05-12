package logic.usecase.state

import data.dto.UserType
import logic.exception.EmptyNameException
import logic.exception.EmptyTitleException
import logic.exception.InvalidUserException
import logic.model.Project
import logic.model.State
import logic.model.User

class ValidationState {
    fun validateOfState( state: State) {
        if (state.title?.isBlank() == true) throw EmptyTitleException()
    }
}