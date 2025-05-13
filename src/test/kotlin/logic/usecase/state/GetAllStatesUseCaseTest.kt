package logic.usecase.state

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.State
import logic.repositories.StatesRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertEquals

class GetAllStatesUseCaseTest {

    private lateinit var statesRepository : StatesRepository
    private lateinit var getAllStatesUseCase : GetAllStatesUseCase

    @BeforeEach
    fun setUp() {
        statesRepository = mockk()
        getAllStatesUseCase = GetAllStatesUseCase(statesRepository)
    }

    @Test
    fun `should return list of states from repository`() = runTest {
        // Given
        val expectedStates = listOf(
            State(id = UUID.randomUUID(), title = "done"),
            State(id = UUID.randomUUID(), title = "in progress")
        )
        coEvery { statesRepository.getAllStates() } returns expectedStates

        // When
        val actualStates = getAllStatesUseCase.getAllStateById()

        // Then
        assertEquals(expectedStates, actualStates)
        coVerify(exactly = 1) { statesRepository.getAllStates() }
    }
}
