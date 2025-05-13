package logic.usecase.state

import data.customException.PlanMateException
import logic.model.State

class ValidationState {
    fun validateState(state: State) {
        if (state.title.isNullOrBlank()) throw PlanMateException.ValidationException.TitleException()
    }
}