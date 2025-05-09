package ui.features.auth

import data.dto.UserType
import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.User
import logic.usecase.user.LoginUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.features.user.UserManagementView
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class LoginManagementViewTest {

    private lateinit var loginUserUseCase : LoginUserUseCase
    private lateinit var inputHandler : InputHandler
    private lateinit var outputFormatter : OutputFormatter
    private lateinit var userManagementView : UserManagementView
    private lateinit var loginManagementView : LoginManagementView

    @BeforeEach
    fun setUp() {
        loginUserUseCase = mockk(relaxed = true)
        inputHandler = mockk(relaxed = true)
        outputFormatter = mockk(relaxed = true)
        userManagementView = mockk(relaxed = true)
        loginManagementView = LoginManagementView(
            loginUserUseCase,
            inputHandler,
            outputFormatter,
            userManagementView
        )
    }
    @Test
    fun `should see login successfuly when username and password is valid` () = runTest {
        val user = User(id = UUID.randomUUID(), name = "nour", password = "123456", type = UserType.MATE)
        every { inputHandler.promptForInput(any()) } returns "nour"
        every { inputHandler.promptForPassword(any()) } returns "123456"
        coEvery { loginUserUseCase.login("nour", "123456") } returns user
        mockkObject(UserSession)
        every { UserSession.setUser(user) } just Runs

        loginManagementView.showLoginScreen()

        verify { UserSession.setUser(user) }
        verify { outputFormatter.printSuccess("🎉 Login successful! Welcome, ${user.name} 🙌") }
        verify { userManagementView.showUserMenu() }
    }

    @Test
    fun `should user see Username cannot be empty error when username is empty`() = runTest {
        every { inputHandler.promptForInput(any()) } returns ""

        loginManagementView.showLoginScreen()

        verify { outputFormatter.printError("❌ Username cannot be empty.") }
        verify { inputHandler.waitForEnter() }
        coVerify(exactly = 0) { loginUserUseCase.login(any(), any()) }
    }

    @Test
    fun `should user see Password cannot be empty error when password is empty`() = runTest {
        every { inputHandler.promptForInput(any()) } returns "nour"
        every { inputHandler.promptForPassword(any()) } returns ""

        loginManagementView.showLoginScreen()

        verify { outputFormatter.printError("❌ Password cannot be empty.") }
        verify { inputHandler.waitForEnter() }
        coVerify(exactly = 0) { loginUserUseCase.login(any(), any()) }
    }



    @Test
    fun `should see Authentication failed Invalid username or password is invalid `() = runTest {
        every { inputHandler.promptForInput(any()) } returns "nour"
        every { inputHandler.promptForPassword(any()) } returns "1234567"
        coEvery { loginUserUseCase.login("nour", "1234567") } throws Exception("Invalid username or password")

        loginManagementView.showLoginScreen()

        verify { outputFormatter.printError(match { it.contains("Authentication failed") }) }
    }
}
