import logic.exception.EmptyTitleException
import logic.model.Task
import logic.usecase.task.ValidationTask
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID
import org.junit.jupiter.api.assertThrows

class ValidationTaskTest {

    private lateinit var validationTask: ValidationTask

    @BeforeEach
    fun setUp() {
        validationTask = ValidationTask()
    }

    @Test
    fun `should throw EmptyTitleException when task title is blank`() {
        // Given
        val task = Task(id = UUID.randomUUID(), title = "  ")

        // When & Then
        assertThrows<EmptyTitleException> {
            validationTask.validateCreateTask(task)
        }
    }

    @Test
    fun `should throw EmptyTitleException when task title is null`() {
        // Given
        val task = Task(id = UUID.randomUUID(), title = null)

        // When & Then
        assertThrows<EmptyTitleException> {
            validationTask.validateCreateTask(task)
        }
    }

    @Test
    fun `should pass when task title is not blank`() {
        // Given
        val task = Task(id = UUID.randomUUID(), title = "test")

        // When & Then
        validationTask.validateCreateTask(task) // No exception should be thrown
    }
}
