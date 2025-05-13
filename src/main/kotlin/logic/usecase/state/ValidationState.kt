package logic.usecase.state

import logic.exception.TitleException
import logic.model.State

class ValidationState {
    fun validateState(state: State) {
        if (state.title?.isBlank() == true) throw TitleException()
    }
}