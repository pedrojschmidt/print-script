import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class FormatterTest {
    @Test
    fun `test 001 - all rules are true`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    StringOperator("Hello"),
                ),
            )

        val formatter = Formatter(FormatRules(true, true, true, 1, true))

        assertEquals("let a : string = \"Hello\";\n", formatter.formatString(ast))
    }

    @Test
    fun `test 002 - no space between let and identifier`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    StringOperator("Hello"),
                ),
            )

        val formatter = Formatter(FormatRules(true, true, true, 1, false))

        assertEquals("leta : string = \"Hello\";\n", formatter.formatString(ast))
    }

    @Test
    fun `test 003 - no space between colon`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    StringOperator("Hello"),
                ),
            )

        val formatter = Formatter(FormatRules(false, false, true, 1, true))

        assertEquals("let a:string = \"Hello\";\n", formatter.formatString(ast))
    }

    @Test
    fun `test 004 - no space between assign`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    StringOperator("Hello"),
                ),
            )

        val formatter = Formatter(FormatRules(true, true, false, 1, true))

        assertEquals("let a : string=\"Hello\";\n", formatter.formatString(ast))
    }

    @Test
    fun `test 005 - all rules are false`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    StringOperator("Hello"),
                ),
            )

        val formatter = Formatter(FormatRules(false, false, false, 1, false))

        assertEquals("leta:string=\"Hello\";\n", formatter.formatString(ast))
    }
}
