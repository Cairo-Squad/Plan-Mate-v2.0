package data.dataSource.localDataSource.file

import data.dataSource.localDataSource.file.handler.CsvFileHandler
import data.dataSource.util.CsvIndices
import data.dto.UserDto
import data.dto.UserType
import logic.model.User
import java.util.*

class UsersCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<UserDto>(
    filePath = filePath,
    columnNames = headers,
    getDtoId = { it.id }
) {

    override fun fromDtoToCsvRow(entity: UserDto): String {
        val rowStringBuilder = StringBuilder()
        rowStringBuilder.append("${entity.id}")
        rowStringBuilder.append(",${entity.name}")
        rowStringBuilder.append(",${entity.password}")
        rowStringBuilder.append(",${entity.type.name}")
        return rowStringBuilder.toString()
    }

    override fun fromCsvRowToDto(row: String): UserDto {
        val userData = row.split(",")
        return UserDto(
            id = UUID.fromString(userData[CsvIndices.USER_ID]),
            name = userData[CsvIndices.USER_NAME],
            password = userData[CsvIndices.USER_PASSWORD],
            type = UserType.valueOf(userData[CsvIndices.USER_TYPE])
        )
    }

}