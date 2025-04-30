package data.hashing

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MD5HashingImplTest {

    lateinit var md5Hashing: MD5Hashing

    @BeforeEach
    fun setUp() {
        md5Hashing = MD5HashingImpl()
    }

    @Test
    fun `hash() should return non-empty hash string, when input is not empty`() {
        // Given
        val input = "Mohamed123"

        // When
        val result = md5Hashing.hash(input)

        // Then
        assertThat(result.length).isEqualTo(32)
    }

    @Test
    fun `hash() should return empty hash string, when input is empty`() {
        // Given
        val input = ""

        // When & Then
        assertThrows<IllegalStateException> {
            md5Hashing.hash(input)
        }
    }

    @Test
    fun `hash() should produce consistent output, when similar inputs are provided`() {
        // Given
        val input1 = "Cairo"
        val input2 = "Cairo"

        // When
        val result1 = md5Hashing.hash(input1)
        val result2 = md5Hashing.hash(input2)

        // Then
        assertThat(result1).isEqualTo(result2)
    }

    @Test
    fun `hash() should produce different hashing, when different inputs are provided`() {
        // Given
        val input1 = "Cairo"
        val input2 = "Ceiro"

        // When
        val result1 = md5Hashing.hash(input1)
        val result2 = md5Hashing.hash(input2)

        // Then
        assertThat(result1).isNotEqualTo(result2)
    }

}