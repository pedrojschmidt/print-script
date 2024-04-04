import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StaticCodeAnalyzerTest {
    @Test
    fun `test 001 - should analyze that the type matched`() {
        val sca = StaticCodeAnalyzer()
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    StringOperator("hello"),
                ),
            )
        val issues = sca.analyze(ast)
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 002 - should analyze that the type did not match`() {
        val sca = StaticCodeAnalyzer()
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    NumberOperator(5.0),
                ),
            )
        val issues = sca.analyze(ast)
        assertEquals(1, issues.size)
        assertEquals("La declaración de variable no coincide con el tipo del valor asignado", issues[0].message)
    }

    @Test
    fun `test 003 - should analyze that the println argument is valid`() {
        val sca = StaticCodeAnalyzer()
        val ast =
            listOf(
                Method(
                    "println",
                    IdentifierOperator("a"),
                ),
            )
        val issues = sca.analyze(ast)
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 004 - should analyze that the println argument is invalid`() {
        val sca = StaticCodeAnalyzer()
        val ast =
            listOf(
                Method(
                    "println",
                    BinaryOperation(
                        IdentifierOperator("a"),
                        "+",
                        StringOperator("world"),
                    ),
                ),
            )
        val issues = sca.analyze(ast)
        assertEquals(1, issues.size)
        assertEquals("La función println debe llamarse solo con un identificador o un literal, la expresión: a + world es inválida.", issues[0].message)
    }

    @Test
    fun `test 005 - should analyze that the identifier is not in lower camel case`() {
        val sca = StaticCodeAnalyzer()
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a_variable", "string"),
                    StringOperator("hello"),
                ),
            )
        val issues = sca.analyze(ast)
        assertEquals(1, issues.size)
        assertEquals("El identificador 'a_variable' debe estar en lower camel case.", issues[0].message)
    }

    @Test
    fun `test 006 - should analyze that the identifier is in lower camel case`() {
        val sca = StaticCodeAnalyzer()
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("aVariable", "string"),
                    StringOperator("hello"),
                ),
            )
        val issues = sca.analyze(ast)
        assertTrue(issues.isEmpty())
    }
}
