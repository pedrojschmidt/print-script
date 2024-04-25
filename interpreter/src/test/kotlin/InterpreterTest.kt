import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InterpreterTest {
    // let a: string = "Hello" + 5;
    // println(a);
    @Test
    fun `test 001 - should interpret an AST and execute it`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    BinaryOperation(
                        StringOperator("Hello"),
                        "+",
                        NumberOperator(5),
                    ),
                    false,
                ),
                Method("println", BinaryOperation(IdentifierOperator("a"), "+", StringOperator(""))),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("Hello5\n", result)
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
                    NumberOperator(1),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("x", "string"),
                    BinaryOperation(
                        IdentifierOperator("a"),
                        "+",
                        StringOperator("Hello"),
                    ),
                    false,
                ),
                Method("println", IdentifierOperator("x")),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("1Hello\n", result)
    }

    // "let a: number = 5 * 5;"
    // "println(a);"
    @Test
    fun `test 003 - should multiply two numbers`() {
        val ast =
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
                Method("println", IdentifierOperator("a")),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("25\n", result)
    }

    // let a: number = 5;
    // let b: number = 5;
    // let c: number = a + b;
    // println(c);
    @Test
    fun `test 004 - should add two numbers`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    NumberOperator(5),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("b", "number"),
                    NumberOperator(5),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("c", "number"),
                    BinaryOperation(
                        IdentifierOperator("a"),
                        "+",
                        IdentifierOperator("b"),
                    ),
                    false,
                ),
                Method("println", IdentifierOperator("c")),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("10\n", result)
    }

    // let a: number = 10;
    // let b: number = 5;
    // let c: number = a - b;
    // println(c);
    @Test
    fun `test 005 - should subtract two numbers`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    NumberOperator(10),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("b", "number"),
                    NumberOperator(5),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("c", "number"),
                    BinaryOperation(
                        IdentifierOperator("a"),
                        "-",
                        IdentifierOperator("b"),
                    ),
                    false,
                ),
                Method("println", IdentifierOperator("c")),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("5\n", result)
    }

    // let a: number = 10;
    // let b: number = 5;
    // let c: number = a / b;
    // println(c);
    @Test
    fun `test 006 - should divide two numbers`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    NumberOperator(10),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("b", "number"),
                    NumberOperator(5),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("c", "number"),
                    BinaryOperation(
                        IdentifierOperator("a"),
                        "/",
                        IdentifierOperator("b"),
                    ),
                    false,
                ),
                Method("println", IdentifierOperator("c")),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("2\n", result)
    }

    // let a: string = "Hello";
    // let b: string = " World";
    // let c: string = a + b;
    // println(c);
    @Test
    fun `test 007 - should concatenate two strings`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    StringOperator("Hello"),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("b", "string"),
                    StringOperator(" World"),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("c", "string"),
                    BinaryOperation(
                        IdentifierOperator("a"),
                        "+",
                        IdentifierOperator("b"),
                    ),
                    false,
                ),
                Method("println", IdentifierOperator("c")),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("Hello World\n", result)
    }

    // let a: string;
    @Test
    fun `test 008 - should interpret a declaration node`() {
        val ast =
            listOf(
                Declaration("a", "string"),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals(null, result)
    }

    // let a: string;
    // a = "Hello";
    @Test
    fun `test 009 - should interpret a simple assignation node`() {
        val ast =
            listOf(
                Declaration("a", "string"),
                SimpleAssignation("a", StringOperator("Hello")),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        println(result)
        assertEquals(null, result)
    }

    // "let result: number = 5 + 5 * 10 - 2 / 2;"
    // "println(result);"
    @Test
    fun `test 013 - should interpret a complex number declaration with assignment`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("result", "number"),
                    BinaryOperation(
                        BinaryOperation(
                            NumberOperator(5),
                            "+",
                            BinaryOperation(
                                NumberOperator(5),
                                "*",
                                NumberOperator(10),
                            ),
                        ),
                        "-",
                        BinaryOperation(
                            NumberOperator(2),
                            "/",
                            NumberOperator(2),
                        ),
                    ),
                    false,
                ),
                Method("println", IdentifierOperator("result")),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("54\n", result)
    }

    // "let x: number = 5.5;"
    @Test
    fun `test 014 - should interpret a number declaration with decimal assignment`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "number"),
                    NumberOperator(5.5),
                    false,
                ),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals(null, result) // No output expected
    }

    // "let sum: number = 5.5 + 5.5;"
    @Test
    fun `test 015 - should interpret a number declaration with decimal addition assignment`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("sum", "number"),
                    BinaryOperation(
                        NumberOperator(5.5),
                        "+",
                        NumberOperator(5.5),
                    ),
                    false,
                ),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals(null, result) // No output expected
    }

    // "let diff: number = 10.5 - 5.5;"
    @Test
    fun `test 016 - should interpret a number declaration with decimal subtraction assignment`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("diff", "number"),
                    BinaryOperation(
                        NumberOperator(10.5),
                        "-",
                        NumberOperator(5.5),
                    ),
                    false,
                ),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals(null, result) // No output expected
    }

    // "let prod: number = 5.5 * 5.5;"
    @Test
    fun `test 017 - should interpret a number declaration with decimal multiplication assignment`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("prod", "number"),
                    BinaryOperation(
                        NumberOperator(5.5),
                        "*",
                        NumberOperator(5.5),
                    ),
                    false,
                ),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals(null, result) // No output expected
    }

    // "let quot: number = 10.5 / 5.5;"
    @Test
    fun `test 018 - should interpret a number declaration with decimal division assignment`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("quot", "number"),
                    BinaryOperation(
                        NumberOperator(10.5),
                        "/",
                        NumberOperator(5.5),
                    ),
                    false,
                ),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals(null, result) // No output expected
    }

    // "let result: number = 5.5 + 5.5 * 10.5 - 2.5 / 2.5;"
    @Test
    fun `test 019 - should interpret a complex number declaration with decimal assignment`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("result", "number"),
                    BinaryOperation(
                        BinaryOperation(
                            NumberOperator(5.5),
                            "+",
                            BinaryOperation(
                                NumberOperator(5.5),
                                "*",
                                NumberOperator(10.5),
                            ),
                        ),
                        "-",
                        BinaryOperation(
                            NumberOperator(2.5),
                            "/",
                            NumberOperator(2.5),
                        ),
                    ),
                    false,
                ),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals(null, result) // No output expected
    }

    @Test
    fun `test 020 - should throw exception for undeclared variable`() {
        val ast =
            listOf(
                SimpleAssignation("x", NumberOperator(5.5)), // "x" is not declared
            )
        val interpreter = Interpreter()
        assertThrows(Exception::class.java) {
            interpreter.interpretAST(ast)
        }
    }

//    @Test
//    fun `test 021 - should throw exception for redeclaration of variable`() {
//        val ast = listOf(
//            Declaration("x", "number"),
//            Declaration("x", "number") // "x" is redeclared
//        )
//        val interpreter = Interpreter()
//        assertThrows(Exception::class.java) {
//            interpreter.consume(ast)
//        }
//    }

    @Test
    fun `test 022 - should throw exception for type mismatch in assignment`() {
        val ast =
            listOf(
                Declaration("x", "number"),
                SimpleAssignation("x", StringOperator("Hello")), // "x" is a number, but we try to assign a string
            )
        val interpreter = Interpreter()
        assertThrows(Exception::class.java) {
            interpreter.interpretAST(ast)
        }
    }

    @Test
    fun `test 023 - should throw exception for unsupported operation`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "number"),
                    BinaryOperation(
                        StringOperator("Hello"),
                        "-",
                        StringOperator("World"), // "-" operation is not supported for strings
                    ),
                    false,
                ),
            )
        val interpreter = Interpreter()
        assertThrows(Exception::class.java) {
            interpreter.interpretAST(ast)
        }
    }

    @Test
    fun `test 024 - should throw exception for unexpected method`() {
        val ast =
            listOf(
                Method("unknownMethod", NumberOperator(5)), // "unknownMethod" is not a supported method
            )
        val interpreter = Interpreter()
        assertThrows(Exception::class.java) {
            interpreter.interpretAST(ast)
        }
    }

//    @Test
//    fun `test 025 - should throw exception for unexpected ASTNode type`() {
//        val ast = listOf(
//            object : ASTNode {} // An unexpected ASTNode type
//        )
//        val interpreter = Interpreter()
//        assertThrows(Exception::class.java) {
//            interpreter.consume(ast)
//        }
//    }

    @Test
    fun `test 026 - should throw exception for variable already declared`() {
        val ast =
            listOf(
                Declaration("x", "number"),
                DeclarationAssignation(
                    Declaration("x", "number"), // "x" is already declared
                    NumberOperator(5),
                    false,
                ),
            )
        val interpreter = Interpreter()
        assertThrows(Exception::class.java) {
            interpreter.interpretAST(ast)
        }
    }

    @Test
    fun `test 027 - should throw exception for variable not declared`() {
        val ast =
            listOf(
                SimpleAssignation("x", NumberOperator(5)), // "x" is not declared
            )
        val interpreter = Interpreter()
        assertThrows(Exception::class.java) {
            interpreter.interpretAST(ast)
        }
    }

//    @Test
//    fun `test 028 - should throw exception for unexpected operation`() {
//        val ast = listOf(
//            DeclarationAssignation(
//                Declaration("x", "number"),
//                object : BinaryNode {} // An unexpected operation
//            )
//        )
//        val interpreter = Interpreter()
//        assertThrows(Exception::class.java) {
//            interpreter.consume(ast)
//        }
//    }

    @Test
    fun `test 029 - should throw exception for variable not declared in operation`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "number"),
                    IdentifierOperator("y"), // "y" is not declared
                    false,
                ),
            )
        val interpreter = Interpreter()
        assertThrows(Exception::class.java) {
            interpreter.interpretAST(ast)
        }
    }

    @Test
    fun `test 030 - should throw exception for unsupported operation with non-numeric operands`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "number"),
                    BinaryOperation(
                        StringOperator("Hello"),
                        "*",
                        StringOperator("World"), // "*" operation is not supported for strings
                    ),
                    false,
                ),
            )
        val interpreter = Interpreter()
        assertThrows(Exception::class.java) {
            interpreter.interpretAST(ast)
        }
    }

    @Test
    fun `test 031 - should throw exception for unsupported operation with non-numeric operands in division`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "number"),
                    BinaryOperation(
                        StringOperator("Hello"),
                        "/",
                        StringOperator("World"), // "/" operation is not supported for strings
                    ),
                    false,
                ),
            )
        val interpreter = Interpreter()
        assertThrows(Exception::class.java) {
            interpreter.interpretAST(ast)
        }
    }

    @Test
    fun `test 032 - should throw exception for invalid operation symbol`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "number"),
                    BinaryOperation(
                        NumberOperator(5),
                        "%", // "%" is not a valid operation
                        NumberOperator(5),
                    ),
                    false,
                ),
            )
        val interpreter = Interpreter()
        assertThrows(Exception::class.java) {
            interpreter.interpretAST(ast)
        }
    }

    @Test
    fun `test 032 - const assignation shouldn't be able to override`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "number"),
                    NumberOperator(1),
                    true,
                ),
                SimpleAssignation(
                    "x",
                    NumberOperator(10),
                ),
            )
        val interpreter = Interpreter()
        val exception =
            assertThrows(Exception::class.java) {
                interpreter.interpretAST(ast)
            }
        assertEquals("Variable x is constant", exception.message)
    }

    @Test
    fun `test 033 - Conditional statement true`() {
        val ast =
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
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("true\n", result)
    }

    @Test
    fun `test 033 - Conditional statement false`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "boolean"),
                    BooleanOperator("false"),
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
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("false\n", result)
    }

    @Test
    fun `test 034 - when define variable in if statement to not appear out of scope`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "boolean"),
                    BooleanOperator("true"),
                    false,
                ),
                Conditional(
                    IdentifierOperator("x"),
                    listOf(
                        DeclarationAssignation(
                            Declaration("y", "number"),
                            NumberOperator(5),
                            false,
                        ),
                    ),
                    null,
                ),
                Method("println", IdentifierOperator("y")),
            )
        val interpreter = Interpreter()
        val exception =
            assertThrows(Exception::class.java) {
                interpreter.interpretAST(ast)
            }
        assertEquals("Variable y not declared", exception.message)
    }

    @Test
    fun `test 034 - change in if statement a variable declare out of the scope`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "number"),
                    NumberOperator(5),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("y", "boolean"),
                    BooleanOperator("true"),
                    false,
                ),
                Conditional(
                    IdentifierOperator("y"),
                    listOf(
                        SimpleAssignation(
                            "x",
                            NumberOperator(10),
                        ),
                    ),
                    null,
                ),
                Method("println", IdentifierOperator("x")),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("10\n", result)
    }

    @Test
    fun `test 035 - when variable define in outer if statement shouldn't be able to define in inner if statement`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "boolean"),
                    BooleanOperator("true"),
                    false,
                ),
                Conditional(
                    IdentifierOperator("x"),
                    listOf(
                        DeclarationAssignation(
                            Declaration("y", "number"),
                            NumberOperator(5),
                            false,
                        ),
                        Conditional(
                            BooleanOperator("true"),
                            listOf(
                                DeclarationAssignation(
                                    Declaration("y", "number"),
                                    NumberOperator(5),
                                    false,
                                ),
                            ),
                            null,
                        ),
                    ),
                    null,
                ),
                Method("println", IdentifierOperator("y")),
            )
        val interpreter = Interpreter()
        val exception =
            assertThrows(Exception::class.java) {
                interpreter.interpretAST(ast)
            }
        assertEquals("Variable y already declared", exception.message)
    }

    @Test
    fun `test 035 - when variable define in outer if statement should be able to use in inner if statement`() {
        val ast =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "boolean"),
                    BooleanOperator("true"),
                    false,
                ),
                DeclarationAssignation(
                    Declaration("y", "number"),
                    NumberOperator(5),
                    false,
                ),
                Conditional(
                    IdentifierOperator("x"),
                    listOf(
                        Method("println", IdentifierOperator("y")),
                    ),
                    null,
                ),
            )
        val interpreter = Interpreter()
        val result = interpreter.interpretAST(ast)
        assertEquals("5\n", result)
    }

//    @Test
//    fun `test 033 - should throw exception for unexpected binary operation node`() {
//        val ast = listOf(
//            DeclarationAssignation(
//                Declaration("x", "number"),
//                object : BinaryNode {} // An unexpected binary operation node
//            )
//        )
//        val interpreter = Interpreter()
//        assertThrows(Exception::class.java) {
//            interpreter.consume(ast)
//        }
//    }
}
