package logic.usecase

import logic.model.UserType
import logic.model.User
import java.util.*

object FakeData {
    val mockUsers = listOf(
        User(id = UUID.randomUUID(), name = "nour", type = UserType.ADMIN),
        User(id = UUID.randomUUID(), name = "nourhan", type = UserType.MATE),
        User(id = UUID.randomUUID(), name = "nourhan", type = UserType.MATE)
    )

    val adminUser = User(UUID.randomUUID(), "ahmed", UserType.ADMIN)
    val mateUser = User(UUID.randomUUID(), "ahmed", UserType.MATE)
}
