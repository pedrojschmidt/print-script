import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InterpreterTest {
    @Test
    fun `test 001 - should interpret an AST and execute it`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    BinaryOperation(
                        StringOperator("Hello"),
                        "+",
                        NumberOperator(5.0),
                    ),
                ),
                Method("println", BinaryOperation(IdentifierOperator("a"), "+", StringOperator(""))),
            )
        val interpreter = Interpreter()
        val result = interpreter.consume(ast)
        assertEquals("Hello5.0\n", result)
    }

    // let a: number = 1.0;
    // let x: string = a + "Hello";
    // println(x);
    @Test
    fun `test 002 - should interpret an AST and execute it`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    NumberOperator(1.0),
                ),
                DeclarationAssignation(
                    Declaration("x", "string"),
                    BinaryOperation(
                        IdentifierOperator("a"),
                        "+",
                        StringOperator("Hello"),
                    ),
                ),
                Method("println", IdentifierOperator("x")),
            )
        val interpreter = Interpreter()
        val result = interpreter.consume(ast)
        assertEquals("1.0Hello\n", result)
    }

    // "let a: string = 5 * 5;"
    // "println(a);"
    @Test
    fun `test 003 - should multiply two numbers`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    BinaryOperation(
                        NumberOperator(5.0),
                        "*",
                        NumberOperator(5.0),
                    ),
                ),
                Method("println", IdentifierOperator("a")),
            )
        val interpreter = Interpreter()
        val result = interpreter.consume(ast)
        assertEquals("25.0\n", result)
    }

    @Test
    fun `test 004 - should add two numbers`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    NumberOperator(5.0),
                ),
                DeclarationAssignation(
                    Declaration("b", "number"),
                    NumberOperator(5.0),
                ),
                DeclarationAssignation(
                    Declaration("c", "number"),
                    BinaryOperation(
                        IdentifierOperator("a"),
                        "+",
                        IdentifierOperator("b"),
                    ),
                ),
                Method("println", IdentifierOperator("c")),
            )
        val interpreter = Interpreter()
        val result = interpreter.consume(ast)
        assertEquals("10.0\n", result)
    }

    @Test
    fun `test 005 - should subtract two numbers`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    NumberOperator(10.0),
                ),
                DeclarationAssignation(
                    Declaration("b", "number"),
                    NumberOperator(5.0),
                ),
                DeclarationAssignation(
                    Declaration("c", "number"),
                    BinaryOperation(
                        IdentifierOperator("a"),
                        "-",
                        IdentifierOperator("b"),
                    ),
                ),
                Method("println", IdentifierOperator("c")),
            )
        val interpreter = Interpreter()
        val result = interpreter.consume(ast)
        assertEquals("5.0\n", result)
    }

    @Test
    fun `test 006 - should divide two numbers`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    NumberOperator(10.0),
                ),
                DeclarationAssignation(
                    Declaration("b", "number"),
                    NumberOperator(5.0),
                ),
                DeclarationAssignation(
                    Declaration("c", "number"),
                    BinaryOperation(
                        IdentifierOperator("a"),
                        "/",
                        IdentifierOperator("b"),
                    ),
                ),
                Method("println", IdentifierOperator("c")),
            )
        val interpreter = Interpreter()
        val result = interpreter.consume(ast)
        assertEquals("2.0\n", result)
    }

    @Test
    fun `test 007 - should concatenate two strings`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    StringOperator("Hello"),
                ),
                DeclarationAssignation(
                    Declaration("b", "string"),
                    StringOperator(" World"),
                ),
                DeclarationAssignation(
                    Declaration("c", "string"),
                    BinaryOperation(
                        IdentifierOperator("a"),
                        "+",
                        IdentifierOperator("b"),
                    ),
                ),
                Method("println", IdentifierOperator("c")),
            )
        val interpreter = Interpreter()
        val result = interpreter.consume(ast)
        assertEquals("Hello World\n", result)
    }

    @Test
    fun `test 008 - should interpret a declaration node`() {
        val ast =
            listOf(
                Declaration("a", "string"),
            )
        val interpreter = Interpreter()
        val result = interpreter.consume(ast)
        assertEquals("", result)
    }

    @Test
    fun `test 009 - should interpret a simple assignation node`() {
        val ast =
            listOf(
                Declaration("a", "string"),
                SimpleAssignation("a", StringOperator("Hello")),
            )
        val interpreter = Interpreter()
        val result = interpreter.consume(ast)
        println(result)
        assertEquals("", result)
    }
}
