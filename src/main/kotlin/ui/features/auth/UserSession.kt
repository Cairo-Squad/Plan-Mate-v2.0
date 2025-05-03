package ui.features.auth

import logic.model.User

object UserSession {
    private var currentUser: User? = null

    fun setUser(user: User) {
        currentUser = user
    }

    fun getUser(): User? {
        return currentUser
    }

    fun clearSession() {
        currentUser = null
    }
}
