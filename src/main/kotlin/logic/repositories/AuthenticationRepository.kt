package logic.repositories

import data.dto.UserDto

interface AuthenticationRepository {
    fun getUser() : List<UserDto>
}