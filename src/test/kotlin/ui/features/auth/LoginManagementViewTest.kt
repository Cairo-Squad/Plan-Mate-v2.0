package ui.features.auth

import data.dto.UserType
import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.User
import logic.usecase.user.GetCurrentUserUseCase
import logic.usecase.user.LoginUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.features.user.UserManagementView
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class LoginManagementViewTest {

    private lateinit var loginUserUseCase: LoginUserUseCase
    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter
    private lateinit var userManagementView: UserManagementView
    private lateinit var loginManagementView: LoginManagementView
    private lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    @BeforeEach
    fun setUp() {
        loginUserUseCase = mockk(relaxed = true)
        inputHandler = mockk(relaxed = true)
        outputFormatter = mockk(relaxed = true)
        userManagementView = mockk(relaxed = true)
        getCurrentUserUseCase = mockk(relaxed = true)
        loginManagementView = LoginManagementView(
            loginUserUseCase,
            inputHandler,
            outputFormatter,
            userManagementView,
            getCurrentUserUseCase
        )
    }

    @Test
    fun `should see login successful when username and password are valid`() = runTest {
        // Given
        val user = User(id = UUID.randomUUID(), name = "nour", password = "123456", type = UserType.MATE)
        every { inputHandler.promptForInput(any()) } returns "nour"
        every { inputHandler.promptForPassword(any()) } returns "123456"
        coEvery { loginUserUseCase.login("nour", "123456") } returns true
        coEvery { getCurrentUserUseCase.getCurrentUser() } returns user

        // When
        loginManagementView.showLoginScreen()

        // Then
        verify { outputFormatter.printSuccess("🎉 Login successful! Welcome, ${user.name} 🙌") }
        verify { userManagementView.showUserMenu() }
        coVerify { getCurrentUserUseCase.getCurrentUser() }
    }

    @Test
    fun `should see Username cannot be empty error when username is empty`() = runTest {
        // Given
        every { inputHandler.promptForInput(any()) } returns ""

        // When
        loginManagementView.showLoginScreen()

        // Then
        verify { outputFormatter.printError("❌ Username cannot be empty.") }
        verify { inputHandler.waitForEnter() }
        coVerify(exactly = 0) { loginUserUseCase.login(any(), any()) }
        coVerify(exactly = 0) { getCurrentUserUseCase.getCurrentUser() }
    }

    @Test
    fun `should see Password cannot be empty error when password is empty`() = runTest {
        // Given
        every { inputHandler.promptForInput(any()) } returns "nour"
        every { inputHandler.promptForPassword(any()) } returns ""

        // When
        loginManagementView.showLoginScreen()

        // Then
        verify { outputFormatter.printError("❌ Password cannot be empty.") }
        verify { inputHandler.waitForEnter() }
        coVerify(exactly = 0) { loginUserUseCase.login(any(), any()) }
        coVerify(exactly = 0) { getCurrentUserUseCase.getCurrentUser() }
    }

    @Test
    fun `should see both Username and Password cannot be empty error when both are empty`() = runTest {
        // Given
        every { inputHandler.promptForInput(any()) } returns ""
        every { inputHandler.promptForPassword(any()) } returns ""

        // When
        loginManagementView.showLoginScreen()

        // Then
        verify { outputFormatter.printError("❌ Username cannot be empty.") }
        verify { inputHandler.waitForEnter() }
        coVerify(exactly = 0) { loginUserUseCase.login(any(), any()) }
        coVerify(exactly = 0) { getCurrentUserUseCase.getCurrentUser() }
    }



    @Test
    fun `should see Invalid username or password message when user login fails`() = runTest {
        // Given
        every { inputHandler.promptForInput(any()) } returns "nour"
        every { inputHandler.promptForPassword(any()) } returns "wrongpassword"
        coEvery { loginUserUseCase.login("nour", "wrongpassword") } returns false

        // When
        loginManagementView.showLoginScreen()

        // Then
        verify { outputFormatter.printError("❌ Invalid username or password.") }
        coVerify { getCurrentUserUseCase.getCurrentUser() }
    }


}
