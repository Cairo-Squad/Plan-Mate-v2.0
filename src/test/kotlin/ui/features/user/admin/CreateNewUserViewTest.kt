package ui.features.user.admin

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.usecase.user.CreateUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class CreateNewUserViewTest {

    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter
    private lateinit var createUserUseCase: CreateUserUseCase
    private lateinit var createNewUserView: CreateNewUserView

    @BeforeEach
    fun setUp() {
        inputHandler = mockk(relaxed = true)
        outputFormatter = mockk(relaxed = true)
        createUserUseCase = mockk(relaxed = true)
        createNewUserView = CreateNewUserView(inputHandler, outputFormatter, createUserUseCase)
    }
    @Disabled
    @Test
    fun `should user see Username cannot be empty error when username is empty`() = runTest {
        //Given
        every { inputHandler.promptForInput(any()) } returns ""
        createNewUserView.createNewUser()

        //When
        verify { outputFormatter.printError("❌ Username cannot be empty.") }
        verify { inputHandler.waitForEnter() }

        //Then
        coVerify(exactly = 0) { createUserUseCase.createUser(any()) }
    }
    @Disabled
    @Test
    fun `should user see  Password cannot be empty error when password is empty`() = runTest {

        //Given
        every { inputHandler.promptForInput(any()) } returns "nour"
        every { inputHandler.promptForPassword(any()) } returns ""
        createNewUserView.createNewUser()

        //When
        verify { outputFormatter.printError("❌ Password cannot be empty.") }
        verify { inputHandler.waitForEnter() }

        //Then
        coVerify(exactly = 0) { createUserUseCase.createUser(any()) }
    }
    @Disabled
    @Test
    fun `should user see created  successfully  when created successfully in database `() = runTest {
        //Given & When
        every { inputHandler.promptForInput(any()) } returns "nour"
        every { inputHandler.promptForPassword(any()) } returns "123456"
        coEvery { createUserUseCase.createUser(any()) } returns true
        createNewUserView.createNewUser()

        //Then
        verify { outputFormatter.printSuccess("✅ User 'nour' created successfully!") }
        verify { inputHandler.waitForEnter() }
    }


}
