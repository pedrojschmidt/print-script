import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class FormatterTest {

    @Test
    fun `test 001 - all rules are true`(){
        val tokens: List<Token> =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 1), Position(3, 1)),
                Token(TokenType.IDENTIFIER, "a", Position(5, 1), Position(5, 1)),
                Token(TokenType.COLON, ":", Position(6, 1), Position(6, 1)),
                Token(TokenType.STRING_TYPE, "string", Position(8, 1), Position(13, 1)),
                Token(TokenType.EQ, "=", Position(15, 1), Position(15, 1)),
                Token(TokenType.NUMBER, "5", Position(17, 1), Position(17, 1)),
                Token(TokenType.SEMICOLON, ";", Position(18, 1), Position(18, 1)),
            )

        val formatter = Formatter(FormatRules(true,true,true,1,true,true))

        assertEquals("let a : string = 5;\n", formatter.format(tokens))
    }

    @Test
    fun `test 002 - no space between let and identifier`(){
        val tokens: List<Token> =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 1), Position(3, 1)),
                Token(TokenType.IDENTIFIER, "a", Position(5, 1), Position(5, 1)),
                Token(TokenType.COLON, ":", Position(6, 1), Position(6, 1)),
                Token(TokenType.STRING_TYPE, "string", Position(8, 1), Position(13, 1)),
                Token(TokenType.EQ, "=", Position(15, 1), Position(15, 1)),
                Token(TokenType.NUMBER, "5", Position(17, 1), Position(17, 1)),
                Token(TokenType.SEMICOLON, ";", Position(18, 1), Position(18, 1)),
            )

        val formatter = Formatter(FormatRules(true,true,true,1,false,true))

        assertEquals("leta : string = 5;\n", formatter.format(tokens))
    }

    @Test
    fun `test 003 - no space between colon`(){
        val tokens: List<Token> =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 1), Position(3, 1)),
                Token(TokenType.IDENTIFIER, "a", Position(5, 1), Position(5, 1)),
                Token(TokenType.COLON, ":", Position(6, 1), Position(6, 1)),
                Token(TokenType.STRING_TYPE, "string", Position(8, 1), Position(13, 1)),
                Token(TokenType.EQ, "=", Position(15, 1), Position(15, 1)),
                Token(TokenType.NUMBER, "5", Position(17, 1), Position(17, 1)),
                Token(TokenType.SEMICOLON, ";", Position(18, 1), Position(18, 1)),
            )

        val formatter = Formatter(FormatRules(false,false,true,1,true,true))

        assertEquals("let a:string = 5;\n", formatter.format(tokens))
    }

    @Test
    fun `test 004 - no space between assign`(){
        val tokens: List<Token> =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 1), Position(3, 1)),
                Token(TokenType.IDENTIFIER, "a", Position(5, 1), Position(5, 1)),
                Token(TokenType.COLON, ":", Position(6, 1), Position(6, 1)),
                Token(TokenType.STRING_TYPE, "string", Position(8, 1), Position(13, 1)),
                Token(TokenType.EQ, "=", Position(15, 1), Position(15, 1)),
                Token(TokenType.NUMBER, "5", Position(17, 1), Position(17, 1)),
                Token(TokenType.SEMICOLON, ";", Position(18, 1), Position(18, 1)),
            )

        val formatter = Formatter(FormatRules(true,true,false,1,true,true))

        assertEquals("let a : string=5;\n", formatter.format(tokens))
    }

    @Test
    fun `test 005 - all rules are false`(){
        val tokens: List<Token> =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 1), Position(3, 1)),
                Token(TokenType.IDENTIFIER, "a", Position(5, 1), Position(5, 1)),
                Token(TokenType.COLON, ":", Position(6, 1), Position(6, 1)),
                Token(TokenType.STRING_TYPE, "string", Position(8, 1), Position(13, 1)),
                Token(TokenType.EQ, "=", Position(15, 1), Position(15, 1)),
                Token(TokenType.NUMBER, "5", Position(17, 1), Position(17, 1)),
                Token(TokenType.SEMICOLON, ";", Position(18, 1), Position(18, 1)),
            )

        val formatter = Formatter(FormatRules(false,false,false,1,false,false))

        assertEquals("leta:string=5;\n", formatter.format(tokens))
    }
}
