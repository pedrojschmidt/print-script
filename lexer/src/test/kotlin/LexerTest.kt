import version_0.Lexer
import version_1.Lexer_1
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class LexerTest {
    // "let a: string = 5 * 5;"
    @Test
    fun `test 001 - should make a list of tokens`() {
        val example = "let a: string = 5 * 5;"
        val lexer = Lexer.getDefaultLexer()
        val tokens = lexer.makeTokens(example)
        assertEquals(9, tokens.size)
    }

    // "println(a);"
    @Test
    fun `test 002 - should make a list of tokens`() {
        val example = "println(a);"
        val lexer = Lexer.getDefaultLexer()
        val tokens = lexer.makeTokens(example)
        assertEquals(5, tokens.size)
    }

    // "const a: boolean = false;"
    @Test
    fun `test 003 - should make a list of tokens`() {
        val example = "const a: boolean = false;"
        val lexer = Lexer_1.getDefaultLexer()
        val tokens = lexer.makeTokens(example)
        assertEquals(7, tokens.size)
    }

    // "if(true){ println(a); } else { println(b); }"
    @Test
    fun `test 004 - should make a list of tokens`() {
        val example = "if(true){ println(a); } else { println(b); }"
        val lexer = Lexer_1.getDefaultLexer()
        val tokens = lexer.makeTokens(example)
        assertEquals(19, tokens.size)
    }
}
