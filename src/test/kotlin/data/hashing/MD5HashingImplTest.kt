package data.hashing

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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

        // When
        val result = md5Hashing.hash(input)

        // Then
        assertThat(result).isEmpty()
    }

}