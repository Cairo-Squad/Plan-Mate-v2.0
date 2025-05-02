import data.dto.UserType
import logic.model.User
import ui.features.user.admin.AdminManagementView
import ui.features.user.mate.MateManagementView

class UserManagementView(
    private val adminManagementView: AdminManagementView,
    private val mateManagementView: MateManagementView,

) {
    fun showUserMenu(user: User) {
        when (user.type) {
            UserType.ADMIN -> adminManagementView.showAdminMenu(user)
            UserType.MATE -> mateManagementView.showMateMenu(user)
        }
    }
}
