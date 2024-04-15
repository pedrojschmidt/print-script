import version_0.TokenProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.FileInputStream

class ParserTest {
    @Test
    fun `test 001 - should convert a list of tokens in ast`() {
        val parser = Parser.getDefaultParser()
        val tokenProvider = TokenProvider(FileInputStream("src/test/resources/test001.txt"))
        val actualAst = parser.generateAST(tokenProvider.readStatement())

        val expectedAst =
            DeclarationAssignation(
                Declaration("a", "string"),
                BinaryOperation(
                    StringOperator("Hello"),
                    "+",
                    StringOperator(" World"),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }
}
