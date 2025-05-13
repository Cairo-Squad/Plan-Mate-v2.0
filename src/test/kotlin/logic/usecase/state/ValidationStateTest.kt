package logic.usecase.state

import data.customException.PlanMateException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import logic.model.State
import java.util.*

class ValidationStateTest {

    private lateinit var validationState: ValidationState

    @BeforeEach
    fun setUp() {
        validationState = ValidationState()
    }

    @Test
    fun `should throw EmptyTitleException when title is blank`() {
        // Given
        val state = State(title = "  ")  // Blank space

        // When & Then
        assertThrows<PlanMateException.ValidationException.TitleException> {
            validationState.validateState(state)  // This should throw the exception
        }
    }

    @Test
    fun `should throw EmptyTitleException when title is null`() {
        // Given
        val state = State(id = UUID.randomUUID(), title = null)

        // When & Then
        assertThrows<PlanMateException.ValidationException.TitleException> {
            validationState.validateState(state)  // This should throw the exception
        }
    }

    @Test
    fun `should not throw exception when title is valid`() {
        // Given
        val state = State(title = "Valid Title")

        // When & Then
        validationState.validateState(state)  // No exception should be thrown
    }
}
