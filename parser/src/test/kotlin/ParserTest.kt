import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParserTest {
    @Test
    fun `test 001 - should convert a list of tokens in ast`() {
        val tokens: List<Token> =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 1), Position(3, 1)),
                Token(TokenType.IDENTIFIER, "a", Position(5, 1), Position(5, 1)),
                Token(TokenType.COLON, ":", Position(6, 1), Position(6, 1)),
                Token(TokenType.STRING_TYPE, "string", Position(8, 1), Position(13, 1)),
                Token(TokenType.EQ, "=", Position(15, 1), Position(15, 1)),
                Token(TokenType.NUMBER, "5", Position(17, 1), Position(17, 1)),
                Token(TokenType.TIMES, "*", Position(19, 1), Position(19, 1)),
                Token(TokenType.NUMBER, "5", Position(21, 1), Position(21, 1)),
                Token(TokenType.SEMICOLON, ";", Position(22, 1), Position(22, 1)),
                Token(TokenType.PRINTLN_FUNCTION, "println", Position(24, 1), Position(30, 1)),
                Token(TokenType.LPAREN, "(", Position(31, 1), Position(31, 1)),
                Token(TokenType.IDENTIFIER, "a", Position(32, 1), Position(32, 1)),
                Token(TokenType.RPAREN, ")", Position(33, 1), Position(33, 1)),
                Token(TokenType.SEMICOLON, ";", Position(34, 1), Position(34, 1)),
            )

        val parser = Parser(tokens)
        val actualAst = parser.generateAST()

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    BinaryOperation(
                        NumberOperator(5),
                        "*",
                        NumberOperator(5),
                    ),
                ),
                Method("println", IdentifierOperator("a")),
            )

        assertEquals(expectedAst, actualAst)
    }
}
