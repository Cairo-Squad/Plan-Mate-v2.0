package ui.features.state

import data.dto.UserType
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import logic.model.User
import logic.usecase.state.CreateStateUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.features.auth.UserSession
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class CreateStateViewTest {

 private lateinit var createStateUseCase: CreateStateUseCase
 private lateinit var inputHandler: InputHandler
 private lateinit var outputFormatter: OutputFormatter

 private lateinit var createStateView: CreateStateView

 private val adminUser = User(UUID.randomUUID(), "adminUser","23131gfdg" ,UserType.ADMIN)
 private val normalUser = User(UUID.randomUUID(), "normalUser", "fdgfdgdfg32",UserType.MATE)

 @BeforeEach
 fun setup() {
  createStateUseCase = mockk()
  inputHandler = mockk()
  outputFormatter = mockk(relaxed = true)

  createStateView = CreateStateView(createStateUseCase, inputHandler, outputFormatter)
 }

 @Test
 fun `createState should create state if user is admin`() {
  mockkObject(UserSession)
  every { UserSession.getUser() } returns adminUser
  every { inputHandler.promptForInput("Enter state title: ") } returns "NewState"
  every { createStateUseCase.createState(any(), eq(adminUser)) } returns true

  createStateView.createState()

  verify { outputFormatter.printHeader("Create a New State") }
  verify { createStateUseCase.createState(match { it.title == "NewState" }, adminUser) }
  verify { outputFormatter.printSuccess("State created successfully!") }
 }

 @Test
 fun `createState should not create state if user is not admin`() {
  mockkObject(UserSession)
  every { UserSession.getUser() } returns normalUser
  every { inputHandler.promptForInput("Enter state title: ") } returns "BlockedState"

  createStateView.createState()

  // ensure use case is never called
  verify(exactly = 0) { createStateUseCase.createState(any(), any()) }
  // ensure no success message printed
  verify(exactly = 0) { outputFormatter.printSuccess(any()) }
  // since no else block → also no error printed
  verify { outputFormatter.printHeader("Create a New State") }
 }

 @Test
 fun `createState should print error if no user authenticated`() {
  mockkObject(UserSession)
  every { UserSession.getUser() } returns null
  every { inputHandler.promptForInput("Enter state title: ") } returns "ShouldFail"

  createStateView.createState()

  verify { outputFormatter.printError("Non authorized access") }
  verify(exactly = 0) { createStateUseCase.createState(any(), any()) }
 }
}
