import formatter.ExecuteFormatter
import formatter.FormatRules
import formatter.FormatterFactory
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class FormatterTests {
    @Test
    fun `test 001 - Should format 2 declaration assignations and their prints`() {
        val astList =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    BinaryOperation(
                        NumberOperator(5),
                        "*",
                        NumberOperator(5),
                    ),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("c", "string"),
                    StringOperator("Hello, world!"),
                    false,
                ),
                Method("println", IdentifierOperator("a")),
                Method("println", IdentifierOperator("c")),
            )

        val formattedAst = formatASTList(astList, "src/main/resources/format_rules.yaml")

        val expectedString =
            "let a : number = 5 * 5;\n" +
                "let c : string = \"Hello, world!\";\n" +
                "\n" +
                "println(a);\n" +
                "\n" +
                "println(c);\n"

        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 002 - Should format 3 declaration assignations and their prints`() {
        val astList =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    BinaryOperation(
                        NumberOperator(5),
                        "*",
                        NumberOperator(5),
                    ),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("b", "number"),
                    BinaryOperation(
                        NumberOperator(10),
                        "+",
                        IdentifierOperator("a"),
                    ),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("c", "string"),
                    BinaryOperation(
                        StringOperator("Resultado: "),
                        "+",
                        IdentifierOperator("b"),
                    ),
                    false,
                ),
                Method("println", IdentifierOperator("a")),
                Method("println", IdentifierOperator("b")),
                Method("println", IdentifierOperator("c")),
            )

        val formattedAst = formatASTList(astList, "src/main/resources/format_rules.yaml")

        val expectedString =
            "let a : number = 5 * 5;\n" +
                "let b : number = 10 + a;\n" +
                "let c : string = \"Resultado: \" + b;\n" +
                "\n" +
                "println(a);\n" +
                "\n" +
                "println(b);\n" +
                "\n" +
                "println(c);\n"

        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 003 - Should format a simple assignation node`() {
        val astList =
            listOf(
                SimpleAssignation(
                    "a",
                    BinaryOperation(
                        NumberOperator(10),
                        "+",
                        NumberOperator(5),
                    ),
                ),
            )
        val formattedAst = formatASTList(astList, "src/main/resources/format_rules.yaml")
        val expectedString = "a = 10 + 5;\n"
        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 004 - Should format method calls with different types of values`() {
        val astList =
            listOf(
                Method("println", StringOperator("Hello, world!")),
                Method("println", NumberOperator(5)),
                Method("println", IdentifierOperator("a")),
            )
        val formattedAst = formatASTList(astList, "src/main/resources/format_rules.yaml")
        val expectedString =
            "\nprintln(\"Hello, world!\");\n" +
                "\nprintln(5);\n" +
                "\nprintln(a);\n"
        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 005 - Should format a binary operation node`() {
        val astList =
            listOf(
                BinaryOperation(
                    NumberOperator(5),
                    "+",
                    NumberOperator(5),
                ),
            )
        val formattedAst = formatASTList(astList, "src/main/resources/format_rules.yaml")
        val expectedString = "5 + 5"
        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 006 - Should format a binary operation node with a string`() {
        val astList =
            listOf(
                BinaryOperation(
                    StringOperator("Hello,"),
                    "+",
                    StringOperator(" world!"),
                ),
            )
        val formattedAst = formatASTList(astList, "src/main/resources/format_rules.yaml")
        val expectedString = "\"Hello,\" + \" world!\""
        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 007 - Should format a declaration assignation node`() {
        val astList =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    NumberOperator(5),
                    false,
                ),
            )
        val formattedAst = formatASTList(astList, "src/main/resources/format_rules.yaml")
        val expectedString = "let a : number = 5;\n"
        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 008 - Should format a conditional AST with else`() {
        // Crear una instancia real de ASTNode
        val astList =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "boolean"),
                    BooleanOperator("true"),
                    false,
                ),
                Conditional(
                    IdentifierOperator("x"),
                    listOf(
                        Method("println", StringOperator("true")),
                    ),
                    listOf(
                        Method("println", StringOperator("false")),
                    ),
                ),
            )

        val formattedAst = formatASTList(astList, "src/main/resources/format_rules.yaml")

        val expectedString =
            "let x : boolean = true;\n" +
                "if (x) {\n" +
                "\tprintln(\"true\");\n" +
                "} else {\n" +
                "\tprintln(\"false\");\n" +
                "}\n"

        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 009 - Should format a conditional AST without else`() {
        // Crear una instancia real de ASTNode
        val astList =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "boolean"),
                    BooleanOperator("true"),
                    false,
                ),
                Conditional(
                    IdentifierOperator("x"),
                    listOf(
                        Method("println", StringOperator("true")),
                    ),
                    null,
                ),
            )

        val formattedAst = formatASTList(astList, "src/main/resources/format_rules.yaml")

        val expectedString =
            "let x : boolean = true;\n" +
                "if (x) {\n" +
                "\tprintln(\"true\");\n" +
                "}\n"

        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 010 - Should format a Declaration node`() {
        val astList =
            listOf(
                Declaration("a", "number"),
            )
        val formattedAst = formatASTList(astList, "src/main/resources/format_rules.yaml")
        val expectedString = "let a : number;\n"
        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 011 - Should format a Declaration node`() {
        val astList =
            listOf(
                Declaration("a", "number"),
            )
        val formattedAst = formatASTList(astList, "src/main/resources/format_rules_2.yaml")
        val expectedString = "let a: number;\n"
        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 012 - Should format a Declaration node`() {
        val astList =
            listOf(
                Declaration("a", "number"),
            )
        val formattedAst = formatASTList(astList, "src/main/resources/format_rules_3.yaml")
        val expectedString = "let a:number;\n"
        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test 013 - Should format a Declaration node`() {
        val astList =
            listOf(
                Declaration("a", "number"),
            )
        val formattedAst = formatASTList(astList, "src/main/resources/format_rules_4.yaml")
        val expectedString = "let a :number;\n"
        assertEquals(expectedString, formattedAst)
    }

    private fun formatASTList(
        astList: List<ASTNode>,
        configFilePath: String,
    ): String {
        val executeFormatter = ExecuteFormatter.getDefaultFormatter()
        var formattedAst = ""
        for (ast in astList) {
            formattedAst += executeFormatter.formatNode(ast, FormatRules(configFilePath), FormatterFactory().assignFormatters())
        }
        return formattedAst
    }
}
