import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class LexerTest {
    @Test
    fun `test 001 - should make a list of tokens`() {
        val example = "let a: string = 5 * 5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(a), COLON, STRING_TYPE, EQ, NUMBER(5), TIMES, NUMBER(5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 002 - should make a list of tokens`() {
        val example = "println(a);"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[PRINTLN_FUNCTION, LPAREN, IDENTIFIER(a), RPAREN, SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 003 - should make a list of tokens`() {
        val example = "let x: number;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(x), COLON, NUMBER_TYPE, SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 004 - should make a list of tokens`() {
        val example = "x = 5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[IDENTIFIER(x), EQ, NUMBER(5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 005 - should make a list of tokens`() {
        val example = "let y: string = 'Hello';"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(y), COLON, STRING_TYPE, EQ, STRING(Hello), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 006 - should make a list of tokens`() {
        val example = "let z: number = 5.5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(z), COLON, NUMBER_TYPE, EQ, NUMBER(5.5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 007 - should make a list of tokens`() {
        val example = "let a: string = 'Hello' + ' world!';"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(a), COLON, STRING_TYPE, EQ, STRING(Hello), PLUS, STRING( world!), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 008 - should make a list of tokens`() {
        val example = "let b: string = 'Hello ' + 5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(b), COLON, STRING_TYPE, EQ, STRING(Hello ), PLUS, NUMBER(5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 009 - should make a list of tokens`() {
        val example = "println(168);"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[PRINTLN_FUNCTION, LPAREN, NUMBER(168), RPAREN, SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 010 - should make a list of tokens`() {
        val example = "println(a);"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[PRINTLN_FUNCTION, LPAREN, IDENTIFIER(a), RPAREN, SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 011 - should generate specific tokens`() {
        val example = "let x: number = 5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(x), COLON, NUMBER_TYPE, EQ, NUMBER(5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 012 - should make a list of tokens for addition`() {
        val example = "let sum: number = 5 + 5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(sum), COLON, NUMBER_TYPE, EQ, NUMBER(5), PLUS, NUMBER(5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 013 - should make a list of tokens for subtraction`() {
        val example = "let diff: number = 10 - 5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(diff), COLON, NUMBER_TYPE, EQ, NUMBER(10), MINUS, NUMBER(5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 014 - should make a list of tokens for multiplication`() {
        val example = "let prod: number = 5 * 5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(prod), COLON, NUMBER_TYPE, EQ, NUMBER(5), TIMES, NUMBER(5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 015 - should make a list of tokens for division`() {
        val example = "let quot: number = 10 / 5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(quot), COLON, NUMBER_TYPE, EQ, NUMBER(10), DIV, NUMBER(5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 016 - should make a list of tokens for addition with decimals`() {
        val example = "let sum: number = 5.5 + 5.5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(sum), COLON, NUMBER_TYPE, EQ, NUMBER(5.5), PLUS, NUMBER(5.5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 017 - should make a list of tokens for subtraction with decimals`() {
        val example = "let diff: number = 10.5 - 5.5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(diff), COLON, NUMBER_TYPE, EQ, NUMBER(10.5), MINUS, NUMBER(5.5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 018 - should make a list of tokens for multiplication with decimals`() {
        val example = "let prod: number = 5.5 * 5.5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(prod), COLON, NUMBER_TYPE, EQ, NUMBER(5.5), TIMES, NUMBER(5.5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 019 - should make a list of tokens for division with decimals`() {
        val example = "let quot: number = 10.5 / 5.5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(quot), COLON, NUMBER_TYPE, EQ, NUMBER(10.5), DIV, NUMBER(5.5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 020 - should make a list of tokens for multiple operations`() {
        val example = "let result: number = 5 + 5 * 10 - 2 / 2;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(result), COLON, NUMBER_TYPE, EQ, NUMBER(5), PLUS, NUMBER(5), TIMES, NUMBER(10), MINUS, NUMBER(2), DIV, NUMBER(2), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    @Test
    fun `test 021 - should make a list of tokens for multiple operations with decimals`() {
        val example = "let result: number = 5.5 + 5.5 * 10.5 - 2.5 / 2.5;"
        val lexer = Lexer()
        val actualTokens = lexer.makeTokens(example)

        val expectedTokensString = "[LET_KEYWORD, IDENTIFIER(result), COLON, NUMBER_TYPE, EQ, NUMBER(5.5), PLUS, NUMBER(5.5), TIMES, NUMBER(10.5), MINUS, NUMBER(2.5), DIV, NUMBER(2.5), SEMICOLON]"
        val actualTokensString = listToString(actualTokens)

        assertEquals(expectedTokensString, actualTokensString)
    }

    private fun listToString(tokens: List<Token>): String {
        return tokens.map {
            when (it.type) {
                TokenType.IDENTIFIER, TokenType.NUMBER, TokenType.STRING -> "${it.type.name}(${it.value})"
                else -> it.type.name
            }
        }.toString()
    }
}
