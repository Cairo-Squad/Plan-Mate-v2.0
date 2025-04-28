import org.example.main
import org.example.sum
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MainKtTest {
    @Test
    fun test_main() {
        main()
    }

    @Test
    fun test_sum() {
        val result = sum(3,5)
        assertEquals(4, result)
    }
}