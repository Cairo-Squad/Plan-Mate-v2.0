package logic.usecase.state

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.State
import logic.repositories.StatesRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.assertEquals

class GetStateByIdUseCaseTest {

    private lateinit var statesRepository : StatesRepository
    private lateinit var getStateByIdUseCase : GetStateByIdUseCase

    @BeforeEach
    fun setup() {
        statesRepository = mockk(relaxed = true)
        getStateByIdUseCase = GetStateByIdUseCase(statesRepository)
    }

    @Test
    fun `should return state when valid id is provided`() = runTest {
        // Given
        val stateId = UUID.randomUUID()
        val expectedState = State(stateId, "done")

        coEvery { statesRepository.getStateById(stateId) } returns expectedState

        // When
        val result = getStateByIdUseCase.getStateById(stateId)

        // Then
        assertEquals(expectedState, result)
    }

    @Test
    fun ` should throw exception when repository throws exception`() = runTest {
        // Given
        val stateId = UUID.randomUUID()

        coEvery { statesRepository.getStateById(stateId) } throws Exception("error during get state ")

        // When & Then
        assertThrows<Exception> {
            getStateByIdUseCase.getStateById(stateId)
        }
    }
}
