package ui.features.user.admin

import io.mockk.*
import logic.usecase.user.CreateUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class CreateNewUserViewTest {

    private lateinit var inputHandler : InputHandler
    private lateinit var outputFormatter : OutputFormatter
    private lateinit var createUserUseCase : CreateUserUseCase
    private lateinit var createNewUserView : CreateNewUserView

    @BeforeEach
    fun setUp() {
        inputHandler = mockk(relaxed = true)
        outputFormatter = mockk(relaxed = true)
        createUserUseCase = mockk()
        createNewUserView = CreateNewUserView(inputHandler, outputFormatter, createUserUseCase)
    }

    @Test
    fun `should user see Username cannot be empty error when username is empty`() {
        every { inputHandler.promptForInput(any()) } returns ""

        createNewUserView.createNewUser()

        verify { outputFormatter.printError("❌ Username cannot be empty.") }
        verify { inputHandler.waitForEnter() }
        verify(exactly = 0) { createUserUseCase.createUser(any(), any(), any(), any()) }
    }

    @Test
    fun `should user see  Password cannot be empty error when password is empty`() {
        every { inputHandler.promptForInput(any()) } returns "nour"
        every { inputHandler.promptForPassword(any()) } returns ""

        createNewUserView.createNewUser()

        verify { outputFormatter.printError("❌ Password cannot be empty.") }
        verify { inputHandler.waitForEnter() }
        verify(exactly = 0) { createUserUseCase.createUser(any(), any(), any(), any()) }
    }

    @Test
    fun `should user see created  successfully  when created successfully in database `() {
        every { inputHandler.promptForInput(any()) } returns "nour"
        every { inputHandler.promptForPassword(any()) } returns "123456"
        every { createUserUseCase.createUser(any(), "nour", "123456", any()) } returns true

        createNewUserView.createNewUser()

        verify { outputFormatter.printSuccess("✅ User 'nour' created successfully!") }
        verify { inputHandler.waitForEnter() }
    }

    @Test
    fun `should user see Failed to create user error if createUserUseCase throws exception`() {
        every { inputHandler.promptForInput(any()) } returns "nour"
        every { inputHandler.promptForPassword(any()) } returns "123456"
        every {
            createUserUseCase.createUser(
                any(),
                any(),
                any(),
                any()
            )
        } throws Exception(" error when create user in database ")

        createNewUserView.createNewUser()

        verify { outputFormatter.printError(match { it.contains("Failed to create user") }) }
        verify { inputHandler.waitForEnter() }
    }
}
