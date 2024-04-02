import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class LexerTest {
    // "let a: string = 5 * 5;"
    @Test
    fun `test 001 - should make a list of tokens`() {
        val example = "let a: string = 5 * 5;"
        val lexer = Lexer(example)
        val tokens = lexer.makeTokens()
        assertEquals(9, tokens.size)
    }

    // "println(a);"
    @Test
    fun `test 002 - should make a list of tokens`() {
        val example = "println(a);"
        val lexer = Lexer(example)
        val tokens = lexer.makeTokens()
        assertEquals(5, tokens.size)
    }
}