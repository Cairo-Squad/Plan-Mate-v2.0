package data.repositories.mappers

import data.dto.UserDto
import logic.model.User
import java.util.UUID

fun UserDto.toUser(): User {
    return User(
        id = this.id,
        name = this.name,
        type = this.type
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        id = this.id,
        name = this.name?:"",
        type = this.type!!
    )
}