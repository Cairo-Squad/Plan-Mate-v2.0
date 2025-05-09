package ui.features.user.admin

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.user.GetAllUsersUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import util.FakeData.getAllUsers

class ListAllUsersViewTest {

    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase
    private lateinit var listAllUsersView: ListAllUsersView

    @BeforeEach
    fun setUp() {
        inputHandler = mockk(relaxed = true)
        outputFormatter = mockk(relaxed = true)
        getAllUsersUseCase = mockk(relaxed = true)
        listAllUsersView = ListAllUsersView(inputHandler, outputFormatter, getAllUsersUseCase)
    }

    @Test
    fun `function list all users should print error if there is no users`() {
        //Given
        coEvery { getAllUsersUseCase.getAllUsers() } returns emptyList()

        //When
        listAllUsersView.listAllUsers()

        //Then
        verify { outputFormatter.printHeader(any()) }
        verify { outputFormatter.printError("❌ No users found in the system!") }
        verify(exactly = 0) { inputHandler.waitForEnter() }
    }

    @Test
    fun `function list all users should print all users when found`() {
        //Given
        coEvery { getAllUsersUseCase.getAllUsers() } returns getAllUsers()

        //When
        listAllUsersView.listAllUsers()

        //Then
        verify { outputFormatter.printHeader(any()) }
        verify { outputFormatter.printInfo("📋 Available Users:") }
        verify { outputFormatter.printInfo("📌 1. Hadeel | 🆔 ID: ${getAllUsers()[0].id} | 🏷️ Type: ${getAllUsers()[0].type}") }
        verify { outputFormatter.printInfo("📌 2. Fathy | 🆔 ID: ${getAllUsers()[1].id} | 🏷️ Type: ${getAllUsers()[1].type}") }
        verify { inputHandler.waitForEnter() }
        verify(exactly = 0) { outputFormatter.printError(any()) }
    }
}