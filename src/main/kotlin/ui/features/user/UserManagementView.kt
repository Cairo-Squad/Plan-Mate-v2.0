package ui.features.user

import data.dto.UserType
import kotlinx.coroutines.runBlocking
import logic.usecase.user.GetCurrentUserUseCase
import ui.features.user.admin.AdminManagementView
import ui.features.user.mate.MateManagementView

class UserManagementView(
    private val adminManagementView: AdminManagementView,
    private val mateManagementView: MateManagementView,
    private val getCurrentUser : GetCurrentUserUseCase

) {
    fun showUserMenu() = runBlocking{
        when (getCurrentUser.getCurrentUser()?.type) {
            UserType.ADMIN -> adminManagementView.showAdminMenu()
            UserType.MATE -> mateManagementView.showMateMenu()
            else -> {
            }
        }
    }
}
