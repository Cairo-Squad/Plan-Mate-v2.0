package Utils

import data.dto.ProjectDto
import java.util.*

object FakeData {
    val fakeProjects = listOf(
        ProjectDto(
            id = UUID.randomUUID(),
            title = "transaction",
            description = "financial",
            tasks = emptyList(),
            createdBy = UUID.randomUUID(),
            stateId = UUID.randomUUID()

        ), ProjectDto(
            id = UUID.randomUUID(),
            title = "food",
            description = "cultural",
            tasks = emptyList(),
            createdBy = UUID.randomUUID(),
            stateId = UUID.randomUUID()
        )
    )

}