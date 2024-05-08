import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import sca.StaticCodeAnalyzer
import sca.StaticCodeAnalyzerRules

class StaticCodeAnalyzerTest {
    @Test
    fun `test 001 - should analyze that the type matched`() {
        val sca = StaticCodeAnalyzer(StaticCodeAnalyzerRules(functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true))
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    StringOperator("hello"),
                    false,
                ),
            )
        val issues = sca.analyze(ast)
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 002 - should analyze that the type did not match`() {
        val sca = StaticCodeAnalyzer(StaticCodeAnalyzerRules(functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true))
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    NumberOperator(5.0),
                    false,
                ),
            )
        val issues = sca.analyze(ast)
        assertEquals(1, issues.size)
        assertEquals("La declaración de variable no coincide con el tipo del valor asignado", issues[0].message)
    }

    @Test
    fun `test 003 - should analyze that the println argument is valid`() {
        val sca = StaticCodeAnalyzer(StaticCodeAnalyzerRules(functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true))
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
        val sca = StaticCodeAnalyzer(StaticCodeAnalyzerRules(functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true))
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
        val sca = StaticCodeAnalyzer(StaticCodeAnalyzerRules(functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true))
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a_variable", "string"),
                    StringOperator("hello"),
                    false,
                ),
            )
        val issues = sca.analyze(ast)
        assertEquals(1, issues.size)
        assertEquals("El identificador 'a_variable' debe estar en lower camel case.", issues[0].message)
    }

    @Test
    fun `test 006 - should analyze that the identifier is in lower camel case`() {
        val sca = StaticCodeAnalyzer(StaticCodeAnalyzerRules(functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true))
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("aVariable", "string"),
                    StringOperator("hello"),
                    false,
                ),
            )
        val issues = sca.analyze(ast)
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 007 - should ignore type matching check when false`() {
        val sca = StaticCodeAnalyzer(StaticCodeAnalyzerRules(functionArgumentCheck = true, typeMatchingCheck = false, identifierNamingCheck = true))
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    NumberOperator(5.0),
                    false,
                ),
            )
        val issues = sca.analyze(ast)
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 008 - should ignore identifier naming check when false`() {
        val sca = StaticCodeAnalyzer(StaticCodeAnalyzerRules(functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = false))
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("Variable", "string"),
                    StringOperator("hello"),
                    false,
                ),
            )
        val issues = sca.analyze(ast)
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 009 - should ignore println argument check when false`() {
        val sca = StaticCodeAnalyzer(StaticCodeAnalyzerRules(functionArgumentCheck = false, typeMatchingCheck = true, identifierNamingCheck = true))
        val ast =
            listOf(
                Method(
                    "println",
                    BinaryOperation(
                        IdentifierOperator("Variable"),
                        "+",
                        NumberOperator(5.0),
                    ),
                ),
            )
        val issues = sca.analyze(ast)
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 010 - should create StaticCodeAnalyzer from YAML content`() {
        val yamlContent =
            """
            rules:
              printlnArgumentCheck: true
              typeMatchingCheck: true
              identifierNamingCheck: true
            """.trimIndent()

        val sca = StaticCodeAnalyzer.fromYaml(yamlContent)

        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("aVariable", "string"),
                    StringOperator("hello"),
                    false,
                ),
            )
        val issues = sca.analyze(ast)
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 011 - should analyze that the type matched for number`() {
        val sca = StaticCodeAnalyzer(StaticCodeAnalyzerRules(functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true))
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    NumberOperator(5.0),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("b", "number"),
                    BinaryOperation(
                        NumberOperator(2.0),
                        "+",
                        NumberOperator(3.0),
                    ),
                    false,
                ),
            )
        val issues = sca.analyze(ast)
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 012 - should throw IllegalArgumentException when YAML content is invalid`() {
        val yamlContent =
            """
            invalidKey:
              printlnArgumentCheck: true
              typeMatchingCheck: true
              identifierNamingCheck: true
            """.trimIndent()

        assertThrows<IllegalArgumentException> {
            StaticCodeAnalyzer.fromYaml(yamlContent)
        }
    }
}
