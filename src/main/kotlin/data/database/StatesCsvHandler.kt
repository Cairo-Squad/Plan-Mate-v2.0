package data.database

import data.dto.StateDto

class StatesCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<StateDto>(
    filePath = filePath,
    headers = headers,
    getDtoId = { it.id }
) {
    override fun fromDtoToCsvRow(entity: StateDto): String {
        return ""
    }

    override fun fromCsvRowToDto(row: String): StateDto {
        return StateDto()
    }
}