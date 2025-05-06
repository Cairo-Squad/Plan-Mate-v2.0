package logic.usecase

import data.dto.UserType
import io.mockk.every
import io.mockk.mockk
import logic.model.State
import logic.model.User
import logic.repositories.StatesRepository
import logic.usecase.state.CreateStateUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
		// Given
		val state = State(UUID.randomUUID(), "Test State")
		val user = User(UUID.randomUUID(), "admin", "pw", UserType.ADMIN)
		
		every { statesRepository.createState(any(), any()) } returns true
		
		// When
		val result = createStateUseCase.createState(state, user)
		
		// Then
		assertTrue(result)
	}
	
	@Test
	fun `should return false when mate user tries to create state`() {
		// Given
		val state = State(UUID.randomUUID(), "Test State")
		val user = User(UUID.randomUUID(), "mateUser", "pw", UserType.MATE)
		
		every { statesRepository.createState(any(), any()) } returns false
		
		// When
		val result = createStateUseCase.createState(state, user)
		
		// Then
		assertFalse(result)
	}

	@Test
	fun `should throw Exception when repository throws exception`() {
		// Given
		val state = State(UUID.randomUUID(), "Test State")
		val user = User(UUID.randomUUID(), "mateUser", "pw", UserType.MATE)
		every { statesRepository.createState(any(), any()) } throws Exception()

		// When & Then
		assertThrows<Exception> { createStateUseCase.createState(state, user) }
	}
}