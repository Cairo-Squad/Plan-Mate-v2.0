package ui.features.user

import data.dto.UserType
import ui.features.auth.UserSession
import ui.features.user.admin.AdminManagementView
import ui.features.user.mate.MateManagementView

class UserManagementView(
    private val adminManagementView: AdminManagementView,
    private val mateManagementView: MateManagementView
    ) {
    fun showUserMenu() {
        when (UserSession.getUser()?.type) {
            UserType.ADMIN -> adminManagementView.showAdminMenu()
            UserType.MATE -> mateManagementView.showMateMenu()
            else -> {
                // TODO: Check this!!
            }
        }
    }
}
