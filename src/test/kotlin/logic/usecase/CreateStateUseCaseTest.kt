package logic.usecase

import data.dto.UserType
import io.mockk.every
import io.mockk.mockk
import logic.model.State
import logic.model.User
import logic.repositories.StatesRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class CreateStateUseCaseTest {
	
	private lateinit var statesRepository: StatesRepository
	private lateinit var createStateUseCase: CreateStateUseCase
	
	@BeforeEach
	fun setup() {
		statesRepository = mockk()
		createStateUseCase = CreateStateUseCase(statesRepository)
	}
	
	@Test
	fun `should return true when admin user creates state`() {
		val state = State(UUID.randomUUID(), "Test State")
		val user = User(UUID.randomUUID(), "admin", "pw", UserType.ADMIN)
		
		every { statesRepository.createState(any(), any()) } returns true
		
		val result = createStateUseCase.createState(state, user)
		
		assertTrue(result)
	}
	
	@Test
	fun `should return false when mate user tries to create state`() {
		val state = State(UUID.randomUUID(), "Test State")
		val user = User(UUID.randomUUID(), "mateUser", "pw", UserType.MATE)
		
		every { statesRepository.createState(any(), any()) } returns false
		
		val result = createStateUseCase.createState(state, user)
		
		assertFalse(result)
	}
}