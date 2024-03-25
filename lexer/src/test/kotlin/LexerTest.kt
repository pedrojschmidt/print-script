import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class LexerTest{
    @Test
    //"let a: string = 5 * 5;"
    fun `test 001 - should make a list of tokens`(){
        val example = "let a: string = 5 * 5;"
        val lexer = Lexer(example)
        val tokens = lexer.makeTokens()
        assertEquals(9, tokens.size)
    }

    @Test
    //"println(a);"
    fun `test 002 - should make a list of tokens`(){
        val example = "println(a);"
        val lexer = Lexer(example)
        val tokens = lexer.makeTokens()
        assertEquals(5, tokens.size)
    }
}