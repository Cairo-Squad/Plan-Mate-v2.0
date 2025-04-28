package logic.usecase

import data.dto.UserDto
import data.dto.UserType
import java.util.*

object FakeData {
    val mockUsers = listOf(
        UserDto(id = UUID.randomUUID(), name = "nour", password = "123456", type = UserType.ADMIN),
        UserDto(id = UUID.randomUUID(), name = "nourhan", password = "12345678", type = UserType.MATE)
    )
}