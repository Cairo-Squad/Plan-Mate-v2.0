package utils

import logic.model.Project
import logic.model.State
import java.util.*

object FakeData {

    val validProject = Project(
        id = UUID.randomUUID(),
        createdBy = UUID.randomUUID(),
        title = "food",
        description = "description",
        tasks = emptyList(),
        state = State(id = UUID.randomUUID(), title = "TODO")
    )

    val invalidProject = Project(
        id = UUID.randomUUID(),
        title = "food",
        description = "description",
        tasks = emptyList(),
        createdBy = UUID.randomUUID(),
        state = State(id = UUID.randomUUID(), title = "TODO")
    )

    val projectWithNoDescription = Project(
        id = UUID.randomUUID(),
        title = "food",
        description = "",
        tasks = emptyList(),
        createdBy = UUID.randomUUID(),
        state = State(id = UUID.randomUUID(), title = "TODO")
    )
}