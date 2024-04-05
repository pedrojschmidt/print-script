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
                Token(TokenType.SEMICOLON, ";", Position(22, 1), Position(22, 1))
            )

        val parser = Parser.createDefault()
        val actualAst = parser.generateAST(tokens)

        val expectedAst =
            DeclarationAssignation(
                    Declaration("a", "string"),
                    BinaryOperation(
                        NumberOperator(5.0),
                        "*",
                        NumberOperator(5.0),
                        )
            )
        assertEquals(expectedAst, actualAst)
    }
}
