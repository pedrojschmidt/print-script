import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParserTest {
    @Test
    fun `test 001 - should convert a list of tokens in ast`() {
        val code = "let a: number = 5 * 5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    BinaryOperation(
                        NumberOperator(5),
                        "*",
                        NumberOperator(5),
                    ),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 002 - should convert a list of tokens in ast for println(a)`() {
        val code = "println(a);"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                Method("println", IdentifierOperator("a")),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 003 - should convert a list of tokens in ast for let x number`() {
        val code = "let x: number;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                Declaration("x", "number"),
            )
        assertEquals(expectedAst, actualAst)
    }

//    @Test
//    fun `test 004 - should convert a list of tokens in ast for x = 5`() {
//        val code = "x = 5;"
//        val actualAst = getAstList(code)
//
//        val expectedAst = listOf(
//            SimpleAssignation("x", NumberOperator(5))
//        )
//        assertEquals(expectedAst, actualAst)
//    }

    @Test
    fun `test 005 - should convert a list of tokens in ast for let y string = 'Hello'`() {
        val code = "let y: string = 'Hello';"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("y", "string"),
                    StringOperator("Hello"),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 006 - should convert a list of tokens in ast for let z number = 5,5`() {
        val code = "let z: number = 5.5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("z", "number"),
                    NumberOperator(5.5),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 007 - should convert a list of tokens in ast for let a string = 'Hello' + ' world!'`() {
        val code = "let a: string = 'Hello' + ' world!';"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "string"),
                    BinaryOperation(
                        StringOperator("Hello"),
                        "+",
                        StringOperator(" world!"),
                    ),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 008 - should convert a list of tokens in ast for let b string = 'Hello ' + 5`() {
        val code = "let b: string = 'Hello ' + 5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("b", "string"),
                    BinaryOperation(
                        StringOperator("Hello "),
                        "+",
                        StringOperator("5"),
                    ),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 009 - should convert a list of tokens in ast for println(168)`() {
        val code = "println(168);"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                Method("println", NumberOperator(168)),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 010 - should convert a list of tokens in ast for println(a) again`() {
        val code = "println(a);"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                Method("println", IdentifierOperator("a")),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 011 - should convert a list of tokens in ast for let x number = 5`() {
        val code = "let x: number = 5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("x", "number"),
                    NumberOperator(5),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 012 - should convert a list of tokens in ast for let sum number = 5 + 5`() {
        val code = "let sum: number = 5 + 5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("sum", "number"),
                    BinaryOperation(
                        NumberOperator(5),
                        "+",
                        NumberOperator(5),
                    ),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 013 - should convert a list of tokens in ast for let diff number = 10 - 5`() {
        val code = "let diff: number = 10 - 5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("diff", "number"),
                    BinaryOperation(
                        NumberOperator(10),
                        "-",
                        NumberOperator(5),
                    ),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 014 - should convert a list of tokens in ast for let prod number = 5 times 5`() {
        val code = "let prod: number = 5 * 5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("prod", "number"),
                    BinaryOperation(
                        NumberOperator(5),
                        "*",
                        NumberOperator(5),
                    ),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 015 - should convert a list of tokens in ast for let quot number = 10 div 5`() {
        val code = "let quot: number = 10 / 5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("quot", "number"),
                    BinaryOperation(
                        NumberOperator(10),
                        "/",
                        NumberOperator(5),
                    ),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 016 - should convert a list of tokens in ast for let sum number = 5,5 + 5,5`() {
        val code = "let sum: number = 5.5 + 5.5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("sum", "number"),
                    BinaryOperation(
                        NumberOperator(5.5),
                        "+",
                        NumberOperator(5.5),
                    ),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 017 - should convert a list of tokens in ast for let diff number = 10,5 - 5,5`() {
        val code = "let diff: number = 10.5 - 5.5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("diff", "number"),
                    BinaryOperation(
                        NumberOperator(10.5),
                        "-",
                        NumberOperator(5.5),
                    ),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 018 - should convert a list of tokens in ast for let prod number = 5,5 times 5,5`() {
        val code = "let prod: number = 5.5 * 5.5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("prod", "number"),
                    BinaryOperation(
                        NumberOperator(5.5),
                        "*",
                        NumberOperator(5.5),
                    ),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 019 - should convert a list of tokens in ast for let quot number = 10,5 div 5,5`() {
        val code = "let quot: number = 10.5 / 5.5;"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("quot", "number"),
                    BinaryOperation(
                        NumberOperator(10.5),
                        "/",
                        NumberOperator(5.5),
                    ),
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

//    @Test
//    fun `test 020 - should convert a list of tokens in ast for let result number = 5 + 5 times 10 - 2 div 2`() {
//        val code = "let result: number = 5 + 5 * 10 - 2 / 2;"
//        val actualAst = getAstList(code)
//
//        val expectedAst = listOf(
//            DeclarationAssignation(
//                Declaration("result", "number"),
//                BinaryOperation(
//                    BinaryOperation(
//                        NumberOperator(5),
//                        "+",
//                        BinaryOperation(
//                            NumberOperator(5),
//                            "*",
//                            NumberOperator(10)
//                        )
//                    ),
//                    "-",
//                    BinaryOperation(
//                        NumberOperator(2),
//                        "/",
//                        NumberOperator(2)
//                    )
//                )
//            )
//        )
//        assertEquals(expectedAst, actualAst)
//    }

//    @Test
//    fun `test 021 - should convert a list of tokens in ast for let result number = 5,5 + 5,5 times 10,5 - 2,5 div 2,5`() {
//        val code = "let result: number = 5.5 + 5.5 * 10.5 - 2.5 / 2.5;"
//        val actualAst = getAstList(code)
//
//        val expectedAst = listOf(
//            DeclarationAssignation(
//                Declaration("result", "number"),
//                BinaryOperation(
//                    BinaryOperation(
//                        NumberOperator(5.5),
//                        "+",
//                        BinaryOperation(
//                            NumberOperator(5.5),
//                            "*",
//                            NumberOperator(10.5)
//                        )
//                    ),
//                    "-",
//                    BinaryOperation(
//                        NumberOperator(2.5),
//                        "/",
//                        NumberOperator(2.5)
//                    )
//                )
//            )
//        )
//        assertEquals(expectedAst, actualAst)
//    }

    private fun getAstList(input: String): MutableList<ASTNode> {
        val tokenProvider = TokenProvider(input.byteInputStream())
        val parser = Parser.getDefaultParser()
        val astList = mutableListOf<ASTNode>()
        while (tokenProvider.hasNextStatement()) {
            val tokens = tokenProvider.readStatement()
            val ast = parser.generateAST(tokens)
            // Add the AST to the list if it is not null
            ast?.let { astList.add(it) }
        }
        return astList
    }
}
