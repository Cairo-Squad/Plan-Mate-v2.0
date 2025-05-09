package ui.features.user.admin

import data.dto.UserType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.model.User
import logic.usecase.user.GetAllUsersUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class ListAllUsersViewTest {

    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase
    private lateinit var listAllUsersView: ListAllUsersView

    @BeforeEach
    fun setUp() {
        inputHandler = mockk(relaxed = true)
        outputFormatter = mockk(relaxed = true)
        getAllUsersUseCase = mockk()
        listAllUsersView = ListAllUsersView(inputHandler, outputFormatter, getAllUsersUseCase)
    }

    @Test
    fun `fun list all users should print error if there is no users`() {
        //Given
        every { getAllUsersUseCase.getAllUsers() } returns emptyList()

        //When
        listAllUsersView.listAllUsers()

        //Then
        verify { outputFormatter.printHeader(any()) }
        verify { outputFormatter.printError("❌ No users found in the system!") }
        verify(exactly = 0) { inputHandler.waitForEnter() }
    }

    @Test
    fun `fun list all users should print all users when found`() {
        //Given
        every { getAllUsersUseCase.getAllUsers() } returns listOf(firstUser, secondUser)

        //When
        listAllUsersView.listAllUsers()

        //Then
        verify { outputFormatter.printHeader(any()) }
        verify { outputFormatter.printInfo("📋 Available Users:") }
        verify { outputFormatter.printInfo("📌 1. Hadeel | 🆔 ID: ${firstUser.id} | 🏷️ Type: ${firstUser.type}") }
        verify { outputFormatter.printInfo("📌 2. Fathy | 🆔 ID: ${secondUser.id} | 🏷️ Type: ${secondUser.type}") }
        verify { inputHandler.waitForEnter() }
        verify(exactly = 0) { outputFormatter.printError(any()) }
    }

    private val firstUser = User(
        UUID.fromString("55555555-1244-1234-1144-55555555"), "Hadeel",
        password = "8474",
        UserType.MATE
    )
    private val secondUser = User(
        UUID.fromString("11111111-2222-3333-2222-11111111"), "Fathy", password = "98665", UserType.ADMIN
    )
}