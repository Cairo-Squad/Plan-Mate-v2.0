package data.database

import data.dto.UserDto

class UsersCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<UserDto>(
    filePath = filePath,
    headers = headers,
    getDtoId = { it.id }
) {

    override fun fromDtoToCsvRow(entity: UserDto): String {
        return ""
    }

    override fun fromCsvRowToDto(row: String): UserDto {
        return UserDto()
    }
}