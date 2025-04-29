package data.dto

sealed class UserAction {
    data object DeleteProject : UserAction()
    data class EditProjectTitle(val oldName: String) : UserAction()
}