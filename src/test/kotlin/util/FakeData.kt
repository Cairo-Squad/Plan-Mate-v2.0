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
    val validProject = Project(
        id = UUID.randomUUID(),
        createdBy = UUID.randomUUID(),
        title = "food",
        description = "description",
        tasks = emptyList(),
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
    val projectWithNoTitle = Project(
        id = UUID.randomUUID(),
        title = "",
        description = "description",
        tasks = emptyList(),
        createdBy = UUID.randomUUID(),
        state = State(id = UUID.randomUUID(), title = "TODO")
    )

    val invalidUser = User(name = "admin", password = "98543", id = UUID.randomUUID(), type = UserType.MATE)
    val validUser = User(name = "admin", password = "76598", id = UUID.randomUUID(), type = UserType.ADMIN)

    fun getAllUsers(): List<User> {
        return listOf(
            User(
                UUID.fromString("55555555-1244-1234-1144-55555555"), "Hadeel",
                password = "8474",
                UserType.MATE
            ),
            User(
                UUID.fromString("11111111-2222-3333-2222-11111111"), "Fathy",
                password = "98665",
                UserType.ADMIN
            )
        )
    }
}