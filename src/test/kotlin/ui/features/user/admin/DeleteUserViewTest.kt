package ui.features.user.admin

import io.mockk.*
import logic.usecase.user.DeleteUserUseCase
import logic.usecase.user.GetAllUsersUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import util.FakeData.getAllUsers
import kotlin.RuntimeException

class DeleteUserViewTest {

    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase
    private lateinit var deleteUserUseCase: DeleteUserUseCase
    private lateinit var deleteUserView: DeleteUserView

    @BeforeEach
    fun setUp() {
        inputHandler = mockk(relaxed = true)
        outputFormatter = mockk(relaxed = true)
        getAllUsersUseCase = mockk(relaxed = true)
        deleteUserUseCase = mockk(relaxed = true)
        deleteUserView = DeleteUserView(inputHandler, outputFormatter, getAllUsersUseCase, deleteUserUseCase)
    }

    @Test
    fun `function delete user should print error if there is no users`() {
        //Given
        every { getAllUsersUseCase.getAllUsers() } returns emptyList()

        //When
        deleteUserView.deleteUser()

        //Then
        verify { outputFormatter.printHeader(any()) }
        verify { outputFormatter.printError("❌ No users available to delete!") }
        verify(exactly = 0) { inputHandler.promptForIntChoice(any(), any()) }
    }

    @Test
    fun `should delete when user confirms deletion`() {
        //Given
        every { getAllUsersUseCase.getAllUsers() } returns getAllUsers()
        every { inputHandler.promptForIntChoice(any(), any()) } returns 1
        every { inputHandler.promptForInput(any()) } returns "YES"

        //When
        deleteUserView.deleteUser()

        //Then
        verify { deleteUserUseCase.deleteUser(getAllUsers()[0].id) }
        verify { outputFormatter.printSuccess("✅ User '${getAllUsers()[0].name}' deleted successfully!") }
        verify { inputHandler.waitForEnter() }
    }

    @Test
    fun `should not delete when user does not confirm deletion`() {
        //Given
        every { getAllUsersUseCase.getAllUsers() } returns getAllUsers()
        every { inputHandler.promptForIntChoice(any(), any()) } returns 1
        every { inputHandler.promptForInput(any()) } returns "NO"

        //When
        deleteUserView.deleteUser()

        //Then
        verify(exactly = 0) { deleteUserUseCase.deleteUser(any()) }
        verify(exactly = 0) { inputHandler.waitForEnter() }
    }

    @Test
    fun `should handle exception when user deletion fails during data retrieval`() {
        //Given
        val exception = RuntimeException("Failed to retrieve data")
        every { getAllUsersUseCase.getAllUsers() } returns getAllUsers()
        every { inputHandler.promptForIntChoice(any(), any()) } returns 2
        every { inputHandler.promptForInput(any()) } returns "YES"
        every { deleteUserUseCase.deleteUser(getAllUsers()[1].id) } throws exception

        //When
        deleteUserView.deleteUser()

        //Then
        verify { outputFormatter.printInfo("🔄 Action canceled. No user was deleted: ${exception.message}") }
        verify { inputHandler.waitForEnter() }
    }
}