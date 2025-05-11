package ui.features.user.admin

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.exception.EntityNotChangedException
import logic.model.User
import logic.usecase.user.EditUserUseCase
import logic.usecase.user.GetAllUsersUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class EditUserViewTest {

    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter
    private lateinit var editUserUseCase: EditUserUseCase
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase
    private lateinit var editUserView: EditUserView

    @BeforeEach
    fun setUp() {
        inputHandler = mockk(relaxed = true)
        outputFormatter = mockk(relaxed = true)
        editUserUseCase = mockk(relaxed = true)
        getAllUsersUseCase = mockk(relaxed = true)
        editUserView = EditUserView(editUserUseCase, getAllUsersUseCase, inputHandler, outputFormatter)
    }

    @Test
    fun `should show error when no users exist`() = runTest {

        //Given & When
        coEvery { getAllUsersUseCase.getAllUsers() } returns emptyList()
        editUserView.editUser()

        //Then

        verify { outputFormatter.printError("❌ No users found!") }
        coVerify(exactly = 0) { inputHandler.promptForIntChoice(any(), any()) }
    }


    @Test
    fun `should show error when no changes detected`() = runTest {
        //Given
        val user = User(UUID.randomUUID(), "Alice", "password", null)

        //When
        coEvery { getAllUsersUseCase.getAllUsers() } returns listOf(user)
        every { inputHandler.promptForIntChoice(any(), any()) } returns 1
        every { inputHandler.promptForInput(any()) } returns user.name!!
        every { inputHandler.promptForPassword(any()) } returns user.password!!
        editUserView.editUser()

        //Then
        verify { outputFormatter.printError("⚠️ No changes detected! User information remains the same.") }
        coVerify(exactly = 0) { editUserUseCase.editUser(any()) }
    }

    @Test
    fun `should update user successfully when valid input is provided`() = runTest {
        //Given
        val user = User(UUID.randomUUID(), "Alice", "password", null)

        //When
        coEvery { getAllUsersUseCase.getAllUsers() } returns listOf(user)
        every { inputHandler.promptForIntChoice(any(), any()) } returns 1
        every { inputHandler.promptForInput(any()) } returns "NewAlice"
        every { inputHandler.promptForPassword(any()) } returns "NewPassword"
        coEvery { editUserUseCase.editUser(any()) } returns true
        editUserView.editUser()

        //Then
        verify { outputFormatter.printSuccess("✅ User 'Alice' updated successfully!") }
        coVerify { editUserUseCase.editUser(any()) }
    }

    @Test
    fun `should handle failure when editUserUseCase throws exception`() = runTest {
        //Given
        val user = User(UUID.randomUUID(), "Alice", "password", null)

        //When
        coEvery { getAllUsersUseCase.getAllUsers() } returns listOf(user)
        every { inputHandler.promptForIntChoice(any(), any()) } returns 1
        every { inputHandler.promptForInput(any()) } returns "NewAlice"
        every { inputHandler.promptForPassword(any()) } returns "NewPassword"
        coEvery { editUserUseCase.editUser(any()) } throws EntityNotChangedException()
        editUserView.editUser()

        //Then
        verify { outputFormatter.printError("🔄 No changes were applied: ${EntityNotChangedException().message}") }
    }
}
