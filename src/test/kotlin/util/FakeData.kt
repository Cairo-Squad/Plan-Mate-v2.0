package util

import logic.model.UserType
import logic.model.Project
import logic.model.State
import logic.model.User
import java.util.*

object FakeData {
    val fakeProjects = listOf(
        Project(
            id = UUID.randomUUID(),
            title = "transaction",
            description = "financial",
            createdBy = UUID.randomUUID(),
            state = State(id = UUID.randomUUID(), title = "ToDo")

        ), Project(
            id = UUID.randomUUID(),
            title = "food",
            description = "cultural",
            createdBy = UUID.randomUUID(),
            state = State(id = UUID.randomUUID(), title = "Done")
        )
    )
    val validProject = Project(
        id = UUID.randomUUID(),
        createdBy = UUID.randomUUID(),
        title = "food",
        description = "description",
        state = State(id = UUID.randomUUID(), title = "TODO")
    )

    val projectWithNoDescription = Project(
        id = UUID.randomUUID(),
        title = "food",
        description = "",
        createdBy = UUID.randomUUID(),
        state = State(id = UUID.randomUUID(), title = "TODO")
    )
    val projectWithNoTitle = Project(
        id = UUID.randomUUID(),
        title = "",
        description = "description",
        createdBy = UUID.randomUUID(),
        state = State(id = UUID.randomUUID(), title = "TODO")
    )

    fun getAllUsers(): List<User> {
        return listOf(
            User(
                UUID.fromString("55555555-1244-1234-1144-55555555"), "Hadeel",
                UserType.MATE
            ),
            User(
                UUID.fromString("11111111-2222-3333-2222-11111111"), "Fathy",
                UserType.ADMIN
            )
        )
    }
}