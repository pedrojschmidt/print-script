import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import sca.ExecuteSca
import sca.ScaFactory
import sca.StaticCodeAnalyzerRules
import sca.StaticCodeIssue

class StaticCodeAnalyzerTest {
    @Test
    fun `test 001 - should analyze that the type matched`() {
        val executeSca = ExecuteSca()
        val issues: MutableList<StaticCodeIssue> = mutableListOf()
        val scaRules = StaticCodeAnalyzerRules("", functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true)
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    StringOperator("hello"),
                    false,
                ),
            )
        for (node in ast) {
            issues.addAll(executeSca.analyzeNode(node, scaRules, ScaFactory().assignAnalyzers()))
        }
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 002 - should analyze that the type did not match`() {
        val executeSca = ExecuteSca()
        val issues: MutableList<StaticCodeIssue> = mutableListOf()
        val scaRules = StaticCodeAnalyzerRules("", functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true)
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    NumberOperator(5.0),
                    false,
                ),
            )
        for (node in ast) {
            issues.addAll(executeSca.analyzeNode(node, scaRules, ScaFactory().assignAnalyzers()))
        }
        assertEquals(1, issues.size)
        assertEquals("Variable declaration does not match the type of the assigned value", issues[0].message)
    }

    @Test
    fun `test 003 - should analyze that the println argument is valid`() {
        val executeSca = ExecuteSca()
        val issues: MutableList<StaticCodeIssue> = mutableListOf()
        val scaRules = StaticCodeAnalyzerRules("", functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true)
        val ast =
            listOf(
                Method(
                    "println",
                    IdentifierOperator("a"),
                ),
            )
        for (node in ast) {
            issues.addAll(executeSca.analyzeNode(node, scaRules, ScaFactory().assignAnalyzers()))
        }
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 004 - should analyze that the println argument is invalid`() {
        val executeSca = ExecuteSca()
        val issues: MutableList<StaticCodeIssue> = mutableListOf()
        val scaRules = StaticCodeAnalyzerRules("", functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true)
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
        for (node in ast) {
            issues.addAll(executeSca.analyzeNode(node, scaRules, ScaFactory().assignAnalyzers()))
        }
        assertEquals(1, issues.size)
        assertEquals("The println function should be called only with an identifier or a literal, the expression: a + world is invalid.", issues[0].message)
    }

    @Test
    fun `test 005 - should analyze that the identifier is not in lower camel case`() {
        val executeSca = ExecuteSca()
        val issues: MutableList<StaticCodeIssue> = mutableListOf()
        val scaRules = StaticCodeAnalyzerRules("", functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true)
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a_variable", "string"),
                    StringOperator("hello"),
                    false,
                ),
            )
        for (node in ast) {
            issues.addAll(executeSca.analyzeNode(node, scaRules, ScaFactory().assignAnalyzers()))
        }
        assertEquals(1, issues.size)
        assertEquals("The identifier 'a_variable' must be in lower camel case.", issues[0].message)
    }

    @Test
    fun `test 006 - should analyze that the identifier is in lower camel case`() {
        val executeSca = ExecuteSca()
        val issues: MutableList<StaticCodeIssue> = mutableListOf()
        val scaRules = StaticCodeAnalyzerRules("", functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true)
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("aVariable", "string"),
                    StringOperator("hello"),
                    false,
                ),
            )
        for (node in ast) {
            issues.addAll(executeSca.analyzeNode(node, scaRules, ScaFactory().assignAnalyzers()))
        }
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 007 - should ignore type matching check when false`() {
        val executeSca = ExecuteSca()
        val issues: MutableList<StaticCodeIssue> = mutableListOf()
        val scaRules = StaticCodeAnalyzerRules("", functionArgumentCheck = true, typeMatchingCheck = false, identifierNamingCheck = true)
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    NumberOperator(5.0),
                    false,
                ),
            )
        for (node in ast) {
            issues.addAll(executeSca.analyzeNode(node, scaRules, ScaFactory().assignAnalyzers()))
        }
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 008 - should ignore identifier naming check when false`() {
        val executeSca = ExecuteSca()
        val issues: MutableList<StaticCodeIssue> = mutableListOf()
        val scaRules = StaticCodeAnalyzerRules("", functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = false)
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("Variable", "string"),
                    StringOperator("hello"),
                    false,
                ),
            )
        for (node in ast) {
            issues.addAll(executeSca.analyzeNode(node, scaRules, ScaFactory().assignAnalyzers()))
        }
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 009 - should ignore println argument check when false`() {
        val executeSca = ExecuteSca()
        val issues: MutableList<StaticCodeIssue> = mutableListOf()
        val scaRules = StaticCodeAnalyzerRules("", functionArgumentCheck = false, typeMatchingCheck = true, identifierNamingCheck = true)
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
        for (node in ast) {
            issues.addAll(executeSca.analyzeNode(node, scaRules, ScaFactory().assignAnalyzers()))
        }
        assertTrue(issues.isEmpty())
    }

    @Test
    fun `test 010 - should analyze that the type matched for number`() {
        val executeSca = ExecuteSca()
        val issues: MutableList<StaticCodeIssue> = mutableListOf()
        val scaRules = StaticCodeAnalyzerRules("", functionArgumentCheck = true, typeMatchingCheck = true, identifierNamingCheck = true)
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
        for (node in ast) {
            issues.addAll(executeSca.analyzeNode(node, scaRules, ScaFactory().assignAnalyzers()))
        }
        assertTrue(issues.isEmpty())
    }
}
