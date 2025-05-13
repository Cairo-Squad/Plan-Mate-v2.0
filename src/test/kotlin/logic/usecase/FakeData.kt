package logic.usecase

import logic.model.UserType
import logic.model.User
import java.util.*

object FakeData {
    val mockUsers = listOf(
        User(id = UUID.randomUUID(), name = "nour", password = "123456", type = UserType.ADMIN),
        User(id = UUID.randomUUID(), name = "nourhan", password = "1234567", type = UserType.MATE),
        User(id = UUID.randomUUID(), name = "nourhan", password = "12345678", type = UserType.MATE)
    )
    
    val adminUser = User(UUID.randomUUID(), "ahmed", "pass", UserType.ADMIN)
    val mateUser = User(UUID.randomUUID(), "ahmed", "pass", UserType.MATE)
}
