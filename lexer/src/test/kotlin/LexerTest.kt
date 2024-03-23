import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LexerTest {

    @Test
    fun `lexer with declaring and assigning a string`() {
        val lexer = Lexer("let x: string = 'Hello';");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(7, tokenList.size)

        assertEquals(TokenType.LET_KEYWORD, tokenList[0].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[1].type)
        assertEquals(TokenType.COLON, tokenList[2].type)
        assertEquals(TokenType.STRING_TYPE, tokenList[3].type)
        assertEquals(TokenType.EQ, tokenList[4].type)
        assertEquals(TokenType.STRING, tokenList[5].type)
        assertEquals(TokenType.SEMICOLON, tokenList[6].type)
    }

    @Test
    fun `lexer with declaring and assigning a number`() {
        val lexer = Lexer("let x: number = 4;");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(7, tokenList.size)

        assertEquals(TokenType.LET_KEYWORD, tokenList[0].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[1].type)
        assertEquals(TokenType.COLON, tokenList[2].type)
        assertEquals(TokenType.NUMBER_TYPE, tokenList[3].type)
        assertEquals(TokenType.EQ, tokenList[4].type)
        assertEquals(TokenType.NUMBER, tokenList[5].type)
        assertEquals(TokenType.SEMICOLON, tokenList[6].type)
    }

    @Test
    fun `lexer with declaring and assigning an identifier`() {
        val lexer = Lexer("let x: number = y;");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(7, tokenList.size)

        assertEquals(TokenType.LET_KEYWORD, tokenList[0].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[1].type)
        assertEquals(TokenType.COLON, tokenList[2].type)
        assertEquals(TokenType.NUMBER_TYPE, tokenList[3].type)
        assertEquals(TokenType.EQ, tokenList[4].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[5].type)
        assertEquals(TokenType.SEMICOLON, tokenList[6].type)
    }

    @Test
    fun `lexer with declaring and assigning a numeric operation`() {
        val lexer = Lexer("let x: number = (1+2-1*2/5);");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(17, tokenList.size)

        assertEquals(TokenType.LET_KEYWORD, tokenList[0].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[1].type)
        assertEquals(TokenType.COLON, tokenList[2].type)
        assertEquals(TokenType.NUMBER_TYPE, tokenList[3].type)
        assertEquals(TokenType.EQ, tokenList[4].type)
        assertEquals(TokenType.LPAREN, tokenList[5].type)
        assertEquals(TokenType.NUMBER, tokenList[6].type)
        assertEquals(TokenType.PLUS, tokenList[7].type)
        assertEquals(TokenType.NUMBER, tokenList[8].type)
        assertEquals(TokenType.MINUS, tokenList[9].type)
        assertEquals(TokenType.NUMBER, tokenList[10].type)
        assertEquals(TokenType.TIMES, tokenList[11].type)
        assertEquals(TokenType.NUMBER, tokenList[12].type)
        assertEquals(TokenType.DIV, tokenList[13].type)
        assertEquals(TokenType.NUMBER, tokenList[14].type)
        assertEquals(TokenType.RPAREN, tokenList[15].type)
        assertEquals(TokenType.SEMICOLON, tokenList[16].type)
    }

    @Test
    fun `lexer with declaring and assigning a sum of string and string`() {
        val lexer = Lexer("let x: string = 'Hello' + 'World' ;");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(9, tokenList.size)

        assertEquals(TokenType.LET_KEYWORD, tokenList[0].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[1].type)
        assertEquals(TokenType.COLON, tokenList[2].type)
        assertEquals(TokenType.STRING_TYPE, tokenList[3].type)
        assertEquals(TokenType.EQ, tokenList[4].type)
        assertEquals(TokenType.STRING, tokenList[5].type)
        assertEquals(TokenType.PLUS, tokenList[6].type)
        assertEquals(TokenType.STRING, tokenList[7].type)
        assertEquals(TokenType.SEMICOLON, tokenList[8].type)
    }

    @Test
    fun `lexer with declaring and assigning a sum of string and number`() {
        val lexer = Lexer("let x: string = 'Hello' + 1;");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(9, tokenList.size)

        assertEquals(TokenType.LET_KEYWORD, tokenList[0].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[1].type)
        assertEquals(TokenType.COLON, tokenList[2].type)
        assertEquals(TokenType.STRING_TYPE, tokenList[3].type)
        assertEquals(TokenType.EQ, tokenList[4].type)
        assertEquals(TokenType.STRING, tokenList[5].type)
        assertEquals(TokenType.PLUS, tokenList[6].type)
        assertEquals(TokenType.NUMBER, tokenList[7].type)
        assertEquals(TokenType.SEMICOLON, tokenList[8].type)
    }

    @Test
    fun `lexer with declaring and assigning a sum of identifiers`() {
        val lexer = Lexer("let x: string = y + z;");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(9, tokenList.size)

        assertEquals(TokenType.LET_KEYWORD, tokenList[0].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[1].type)
        assertEquals(TokenType.COLON, tokenList[2].type)
        assertEquals(TokenType.STRING_TYPE, tokenList[3].type)
        assertEquals(TokenType.EQ, tokenList[4].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[5].type)
        assertEquals(TokenType.PLUS, tokenList[6].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[7].type)
        assertEquals(TokenType.SEMICOLON, tokenList[8].type)
    }

    @Test
    fun `lexer with printing an identifier`() {
        val lexer = Lexer("println(x);");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(5, tokenList.size)

        assertEquals(TokenType.PRINTLN_FUNCTION, tokenList[0].type)
        assertEquals(TokenType.LPAREN, tokenList[1].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[2].type)
        assertEquals(TokenType.RPAREN, tokenList[3].type)
        assertEquals(TokenType.SEMICOLON, tokenList[4].type)
    }

    @Test
    fun `lexer with printing a number`() {
        val lexer = Lexer("println(1);");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(5, tokenList.size)

        assertEquals(TokenType.PRINTLN_FUNCTION, tokenList[0].type)
        assertEquals(TokenType.LPAREN, tokenList[1].type)
        assertEquals(TokenType.NUMBER, tokenList[2].type)
        assertEquals(TokenType.RPAREN, tokenList[3].type)
        assertEquals(TokenType.SEMICOLON, tokenList[4].type)
    }

    @Test
    fun `lexer with printing a string`() {
        val lexer = Lexer("println('Hello');");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(5, tokenList.size)

        assertEquals(TokenType.PRINTLN_FUNCTION, tokenList[0].type)
        assertEquals(TokenType.LPAREN, tokenList[1].type)
        assertEquals(TokenType.STRING, tokenList[2].type)
        assertEquals(TokenType.RPAREN, tokenList[3].type)
        assertEquals(TokenType.SEMICOLON, tokenList[4].type)
    }

    @Test
    fun `lexer with printing a sum of string and string`() {
        val lexer = Lexer("println('Hello' + 'World');");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(7, tokenList.size)

        assertEquals(TokenType.PRINTLN_FUNCTION, tokenList[0].type)
        assertEquals(TokenType.LPAREN, tokenList[1].type)
        assertEquals(TokenType.STRING, tokenList[2].type)
        assertEquals(TokenType.PLUS, tokenList[3].type)
        assertEquals(TokenType.STRING, tokenList[4].type)
        assertEquals(TokenType.RPAREN, tokenList[5].type)
        assertEquals(TokenType.SEMICOLON, tokenList[6].type)
    }

    @Test
    fun `lexer with printing a sum of identifiers`() {
        val lexer = Lexer("println(x + y);");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(7, tokenList.size)

        assertEquals(TokenType.PRINTLN_FUNCTION, tokenList[0].type)
        assertEquals(TokenType.LPAREN, tokenList[1].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[2].type)
        assertEquals(TokenType.PLUS, tokenList[3].type)
        assertEquals(TokenType.IDENTIFIER, tokenList[4].type)
        assertEquals(TokenType.RPAREN, tokenList[5].type)
        assertEquals(TokenType.SEMICOLON, tokenList[6].type)
    }

    @Test
    fun `lexer with printing a sum of string and number`() {
        val lexer = Lexer("println('Hello' + 1);");
        val tokenList: List<Token> = lexer.makeTokens();

        assertEquals(7, tokenList.size)

        assertEquals(TokenType.PRINTLN_FUNCTION, tokenList[0].type)
        assertEquals(TokenType.LPAREN, tokenList[1].type)
        assertEquals(TokenType.STRING, tokenList[2].type)
        assertEquals(TokenType.PLUS, tokenList[3].type)
        assertEquals(TokenType.NUMBER, tokenList[4].type)
        assertEquals(TokenType.RPAREN, tokenList[5].type)
        assertEquals(TokenType.SEMICOLON, tokenList[6].type)
    }
}