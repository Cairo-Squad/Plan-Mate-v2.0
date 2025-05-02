package utils

import logic.model.Project
import logic.model.State
import java.util.*

object FakeData {
    val fakeProjects = listOf(
        Project(
            id = UUID.randomUUID(),
            title = "transaction",
            description = "financial",
            tasks = emptyList(),
            createdBy = UUID.randomUUID(),
            state = State(id = UUID.randomUUID(), title = "ToDo")

        ), Project(
            id = UUID.randomUUID(),
            title = "food",
            description = "cultural",
            tasks = emptyList(),
            createdBy = UUID.randomUUID(),
            state = State(id = UUID.randomUUID(), title = "Done")
        )
    )

}