package data.dataSource.localDataSource.file

import data.dataSource.localDataSource.file.handler.CsvFileHandler
import data.dataSource.util.CsvIndices
import data.dto.StateDto
import java.util.*

class StatesCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<StateDto>(
    filePath = filePath,
    columnNames = headers,
    getDtoId = { it.id!! }
) {
    override fun fromDtoToCsvRow(entity: StateDto): String {
        val rowStringBuilder = StringBuilder()
        rowStringBuilder.append("${entity.id}")
        rowStringBuilder.append(",${entity.title}")
        return rowStringBuilder.toString()
    }

    override fun fromCsvRowToDto(row: String): StateDto {
        val logData = row.split(",")
        return StateDto(
            id = UUID.fromString(logData[CsvIndices.STATE_ID]),
            title = logData[CsvIndices.STATE_TITLE]
        )
    }
}