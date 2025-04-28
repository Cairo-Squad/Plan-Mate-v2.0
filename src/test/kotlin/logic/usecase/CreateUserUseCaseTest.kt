package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.dto.UserType
import io.mockk.mockk
import logic.model.User
import logic.repositories.AuthenticationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class CreateUserUseCaseTest {
	private lateinit var repository: AuthenticationRepository
	private lateinit var createUserUseCase: CreateUserUseCase
	
	@BeforeEach
	fun setup() {
		repository = mockk(relaxed = true)
		createUserUseCase = CreateUserUseCase(repository)
	}
	
	@Test
	fun `should success registration when all inputs are valid`(){
		// Given
		val user = User(name = "ahmed", password = "123456", type = UserType.ADMIN)
		
		// When
		val result = createUserUseCase.createUser(user)
		
		// Then
		assertThat(result.isSuccess).isTrue()
	}
	
	@Test
	fun `should fail registration when name is empty`(){
		// Given
		val user = User(name = "", password = "123456", type = UserType.ADMIN)
		
		// When
		val result = createUserUseCase.createUser(user)
		
		// Then
		assertThat(result.isFailure).isTrue()
	}
	
	@Test
	fun `should fail registration when password is empty`(){
		// Given
		val user = User(name = "ahmed", password = "", type = UserType.ADMIN)
		
		// When
		val result = createUserUseCase.createUser(user)
		
		// Then
		assertThat(result.isFailure).isTrue()
	}
	
	@Test
	fun `should success registration when duplication name but different id`(){
		// Given
		val user = User(id = UUID.randomUUID() , name = "ahmed", password = "password1", type = UserType.ADMIN)
		val anotherUserWithSameName = User(id = UUID.randomUUID() , name = "ahmed", password = "password2", type = UserType.ADMIN)
		
		// When
		val firstUser = createUserUseCase.createUser(user)
		val secondUser = createUserUseCase.createUser(anotherUserWithSameName)
		
		// Then
		assertThat(firstUser.isSuccess && secondUser.isSuccess).isTrue()
	}
}