import data.dto.UserType
import io.mockk.*
import logic.model.User
import logic.usecase.user.LoginUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.features.auth.LoginFeatureUI
import ui.features.auth.UserSession
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class LoginFeatureUITest {

    private lateinit var loginUserUseCase: LoginUserUseCase
    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter
    private lateinit var loginFeatureUI: LoginFeatureUI

    @BeforeEach
    fun setup() {
        loginUserUseCase = mockk()
        inputHandler = mockk()
        outputFormatter = mockk(relaxed = true) // relaxed to avoid manually stubbing void functions
        loginFeatureUI = LoginFeatureUI(loginUserUseCase, inputHandler, outputFormatter)
    }

    @Test
    fun `showLoginScreen should login successfully and print success message`() {
        // Arrange
        val username = "testUser"
        val password = "testPass"
        val user = User(id = UUID.randomUUID(), name = username, password = password, type = UserType.MATE)

        every { inputHandler.promptForInput("Username: ") } returns username
        every { inputHandler.promptForPassword("Password: ") } returns password
        every { loginUserUseCase.login(username, password) } returns user
        mockkObject(UserSession)
        every { UserSession.setUser(user) } just Runs

        // Act
        loginFeatureUI.showLoginScreen()

        // Assert
        verify(exactly = 1) { outputFormatter.printHeader("PlanMate - Login") }
        verify(exactly = 1) { inputHandler.promptForInput("Username: ") }
        verify(exactly = 1) { inputHandler.promptForPassword("Password: ") }
        verify(exactly = 1) { loginUserUseCase.login(username, password) }
        verify(exactly = 1) { UserSession.setUser(user) }
        verify(exactly = 1) { outputFormatter.printSuccess("Login successful! Welcome, ${user.name}") }
        verify(exactly = 0) { outputFormatter.printError(any()) }
    }

    @Test
    fun `showLoginScreen should print error message when login fails`() {
        // Arrange
        val username = "testUser"
        val password = "wrongPass"
        val exception = Exception("Invalid credentials")
        mockkObject(UserSession)

        every { inputHandler.promptForInput("Username: ") } returns username
        every { inputHandler.promptForPassword("Password: ") } returns password
        every { loginUserUseCase.login(username, password) } throws exception

        // Act
        loginFeatureUI.showLoginScreen()

        // Assert
        verify(exactly = 1) { outputFormatter.printHeader("PlanMate - Login") }
        verify(exactly = 1) { inputHandler.promptForInput("Username: ") }
        verify(exactly = 1) { inputHandler.promptForPassword("Password: ") }
        verify(exactly = 1) { loginUserUseCase.login(username, password) }
        verify(exactly = 0) { UserSession.setUser(any()) }
        verify(exactly = 1) { outputFormatter.printError("Authentication failed: ${exception.message}") }
        verify(exactly = 0) { outputFormatter.printSuccess(any()) }
    }
}
