package data.hashing

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MD5PasswordHasherImplTest {

    lateinit var hashing: PasswordHasher

    @BeforeEach
    fun setUp() {
        hashing = MD5PasswordHasherImpl()
    }

    @Test
    fun `hash() should return non-empty hash string, when input is not empty`() {
        // Given
        val input = "Mohamed123"

        // When
        val result = hashing.hashPassword(input)

        // Then
        assertThat(result.length).isEqualTo(32)
    }

    @Test
    fun `hash() should return empty hash string, when input is empty`() {
        // Given
        val input = ""

        // When & Then
        assertThrows<IllegalStateException> {
            hashing.hashPassword(input)
        }
    }

    @Test
    fun `hash() should produce consistent output, when similar inputs are provided`() {
        // Given
        val input1 = "Cairo"
        val input2 = "Cairo"

        // When
        val result1 = hashing.hashPassword(input1)
        val result2 = hashing.hashPassword(input2)

        // Then
        assertThat(result1).isEqualTo(result2)
    }

    @Test
    fun `hash() should produce different hashing, when different inputs are provided`() {
        // Given
        val input1 = "Cairo"
        val input2 = "Ceiro"

        // When
        val result1 = hashing.hashPassword(input1)
        val result2 = hashing.hashPassword(input2)

        // Then
        assertThat(result1).isNotEqualTo(result2)
    }

}