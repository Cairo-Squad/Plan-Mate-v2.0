package logic.usecase.state

import logic.exception.EmptyTitleException
import logic.model.State

class ValidationState {
    fun validateState(state: State) {
        if (state.title.isNullOrBlank() == true) throw EmptyTitleException()
    }
}