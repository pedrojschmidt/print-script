import builder.MethodASTBuilder
import builder.ValueASTBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
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
                    false,
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
                    false,
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
                    false,
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
                    false,
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
                    false,
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
                    false,
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
                    false,
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
                    false,
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
                    false,
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
                    false,
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
                    false,
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
                    false,
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
                    false,
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
                    false,
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 020 - should convert a list of tokens in ast for let result number = 5 + 5 times 10 - 2 div 2`() {
        val code = "let result: number = 5 + 5 * 10 - 2 / 2;"
        val actualAst = getAstList(code)

        val expectedAst =
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
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 021 - should convert a list of tokens in ast for let result number = 5,5 + 5,5 times 10,5 - 2,5 div 2,5`() {
        val code = "let result: number = 5.5 + 5.5 * 10.5 - 2.5 / 2.5;"
        val actualAst = getAstList(code)

        val expectedAst =
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
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 022 - should convert a list of tokens in ast for let a number = 5 + (5 times 5)`() {
        val code = "let a: number = 5 + (5 * 5);"
        val actualAst = getAstList(code)

        val expectedAst =
            listOf(
                DeclarationAssignation(
                    Declaration("a", "number"),
                    BinaryOperation(
                        NumberOperator(5),
                        "+",
                        BinaryOperation(
                            NumberOperator(5),
                            "*",
                            NumberOperator(5),
                        ),
                    ),
                    false,
                ),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 023 - should return false for invalid statement`() {
        val tokens =
            listOf(
                Token(TokenType.PRINTLN_FUNCTION, "println", Position(0, 0), Position(0, 6)),
                Token(TokenType.LPAREN, "(", Position(0, 7), Position(0, 8)),
                // Falta el token RPAREN
            )
        val builder = MethodASTBuilder("1.0")
        val result = builder.verify(tokens)
        assertFalse(result)
    }

    @Test
    fun `test 024 - should return false for first token not PRINTLN_FUNCTION`() {
        val tokens =
            listOf(
                Token(TokenType.IDENTIFIER, "println", Position(0, 0), Position(0, 6)), // Cambiado a IDENTIFIER
                Token(TokenType.LPAREN, "(", Position(0, 7), Position(0, 8)),
                Token(TokenType.RPAREN, ")", Position(0, 9), Position(0, 10)),
                Token(TokenType.SEMICOLON, ";", Position(0, 11), Position(0, 12)),
            )
        val builder = MethodASTBuilder("1.0")
        val result = builder.verify(tokens)
        assertFalse(result)
    }

    @Test
    fun `test 025 - should return false for second token not LPAREN`() {
        val tokens =
            listOf(
                Token(TokenType.PRINTLN_FUNCTION, "println", Position(0, 0), Position(0, 6)),
                Token(TokenType.IDENTIFIER, "(", Position(0, 7), Position(0, 8)), // Cambiado a IDENTIFIER
                Token(TokenType.RPAREN, ")", Position(0, 9), Position(0, 10)),
                Token(TokenType.SEMICOLON, ";", Position(0, 11), Position(0, 12)),
            )
        val builder = MethodASTBuilder("1.0")
        val result = builder.verify(tokens)
        assertFalse(result)
    }

    @Test
    fun `test 026 - should return false for second last token not RPAREN`() {
        val tokens =
            listOf(
                Token(TokenType.PRINTLN_FUNCTION, "println", Position(0, 0), Position(0, 6)),
                Token(TokenType.LPAREN, "(", Position(0, 7), Position(0, 8)),
                Token(TokenType.IDENTIFIER, ")", Position(0, 9), Position(0, 10)), // Cambiado a IDENTIFIER
                Token(TokenType.SEMICOLON, ";", Position(0, 11), Position(0, 12)),
            )
        val builder = MethodASTBuilder("1.0")
        val result = builder.verify(tokens)
        assertFalse(result)
    }

    @Test
    fun `test 027 - should throw exception for unverifiable tokens`() {
        val tokens =
            listOf(
                Token(TokenType.IDENTIFIER, "invalid", Position(0, 0), Position(0, 6)),
                // Esta lista de tokens no puede ser verificada por ninguno de los astBuilders
            )
        val parser = Parser.getDefaultParser()
        assertNull(parser.generateAST(tokens))
    }

    @Test
    fun `test 028 - should throw exception when tokens remain after building expression`() {
        val tokens =
            listOf(
                Token(TokenType.NUMBER, "1", Position(0, 0), Position(0, 1)),
                Token(TokenType.PLUS, "+", Position(0, 2), Position(0, 3)),
                Token(TokenType.NUMBER, "2", Position(0, 4), Position(0, 5)),
                Token(TokenType.NUMBER, "3", Position(0, 6), Position(0, 7)), // Token extra
            )
        val builder = ValueASTBuilder()
        assertThrows(RuntimeException::class.java) {
            builder.build(tokens)
        }
    }

    @Test
    fun `test 029 - should throw exception for unexpected token type`() {
        val tokens =
            listOf(
                Token(TokenType.UNKNOWN, "???", Position(0, 0), Position(0, 3)), // Tipo de token inesperado
            )
        val builder = ValueASTBuilder()
        assertThrows(RuntimeException::class.java) {
            builder.build(tokens)
        }
    }

    @Test
    fun `test 030 - should throw exception for mismatched parentheses in expression`() {
        val tokens =
            listOf(
                Token(TokenType.LPAREN, "(", Position(0, 0), Position(0, 1)),
                Token(TokenType.NUMBER, "1", Position(0, 2), Position(0, 3)),
                Token(TokenType.PLUS, "+", Position(0, 4), Position(0, 5)),
                Token(TokenType.NUMBER, "2", Position(0, 6), Position(0, 7)),
                // Falta un token RPAREN para cerrar el paréntesis
            )
        val builder = ValueASTBuilder()
        assertThrows(RuntimeException::class.java) {
            builder.build(tokens)
        }
    }

    @Test
    fun `test031 - if statement`() {
        val tokens =
            listOf(
                Token(TokenType.IF_KEYWORD, "if", Position(0, 0), Position(0, 2)),
                Token(TokenType.LPAREN, "(", Position(0, 3), Position(0, 4)),
                Token(TokenType.IDENTIFIER, "a", Position(0, 5), Position(0, 6)),
                Token(TokenType.RPAREN, ")", Position(0, 7), Position(0, 9)),
                Token(TokenType.LBRACE, "{", Position(0, 10), Position(0, 11)),
                Token(TokenType.NEW_LINE, "\n", Position(0, 12), Position(1, 0)),
                Token(TokenType.PRINTLN_FUNCTION, "println", Position(1, 1), Position(1, 2)),
                Token(TokenType.LPAREN, "(", Position(1, 3), Position(1, 4)),
                Token(TokenType.STRING, "Hello", Position(1, 5), Position(1, 10)),
                Token(TokenType.RPAREN, ")", Position(1, 11), Position(1, 12)),
                Token(TokenType.SEMICOLON, ";", Position(1, 13), Position(1, 14)),
                Token(TokenType.NEW_LINE, "\n", Position(1, 15), Position(2, 0)),
                Token(TokenType.RBRACE, "}", Position(2, 1), Position(2, 2)),
            )
        val parser = Parser.getDefaultParser()
        val actualAst = parser.generateAST(tokens)
        val expectedAst =
            Conditional(
                IdentifierOperator("a"),
                listOf(Method("println", StringOperator("Hello"))),
                null,
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test032 - if else statement`() {
        val tokens =
            listOf(
                Token(TokenType.IF_KEYWORD, "if", Position(0, 0), Position(0, 2)),
                Token(TokenType.LPAREN, "(", Position(0, 3), Position(0, 4)),
                Token(TokenType.IDENTIFIER, "a", Position(0, 5), Position(0, 6)),
                Token(TokenType.RPAREN, ")", Position(0, 7), Position(0, 9)),
                Token(TokenType.LBRACE, "{", Position(0, 10), Position(0, 11)),
                Token(TokenType.NEW_LINE, "\n", Position(0, 12), Position(1, 0)),
                Token(TokenType.PRINTLN_FUNCTION, "println", Position(1, 1), Position(1, 2)),
                Token(TokenType.LPAREN, "(", Position(1, 3), Position(1, 4)),
                Token(TokenType.STRING, "Hello", Position(1, 5), Position(1, 10)),
                Token(TokenType.RPAREN, ")", Position(1, 11), Position(1, 12)),
                Token(TokenType.SEMICOLON, ";", Position(1, 13), Position(1, 14)),
                Token(TokenType.NEW_LINE, "\n", Position(1, 15), Position(2, 0)),
                Token(TokenType.RBRACE, "}", Position(2, 1), Position(2, 2)),
                Token(TokenType.ELSE_KEYWORD, "else", Position(2, 3), Position(2, 7)),
                Token(TokenType.LBRACE, "{", Position(2, 8), Position(2, 9)),
                Token(TokenType.NEW_LINE, "\n", Position(2, 10), Position(3, 0)),
                Token(TokenType.PRINTLN_FUNCTION, "println", Position(3, 1), Position(3, 2)),
                Token(TokenType.LPAREN, "(", Position(3, 3), Position(3, 4)),
                Token(TokenType.STRING, "World", Position(3, 5), Position(3, 10)),
                Token(TokenType.RPAREN, ")", Position(3, 11), Position(3, 12)),
                Token(TokenType.SEMICOLON, ";", Position(3, 13), Position(3, 14)),
                Token(TokenType.NEW_LINE, "\n", Position(3, 15), Position(4, 0)),
                Token(TokenType.RBRACE, "}", Position(4, 1), Position(4, 2)),
            )
        val parser = Parser.getDefaultParser()
        val actualAst = parser.generateAST(tokens)
        val expectedAst =
            Conditional(
                IdentifierOperator("a"),
                listOf(Method("println", StringOperator("Hello"))),
                listOf(Method("println", StringOperator("World"))),
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test033 - if statement with if statement inside`() {
        val tokens =
            listOf(
                Token(TokenType.IF_KEYWORD, "if", Position(0, 0), Position(0, 2)),
                Token(TokenType.LPAREN, "(", Position(0, 3), Position(0, 4)),
                Token(TokenType.IDENTIFIER, "a", Position(0, 5), Position(0, 6)),
                Token(TokenType.RPAREN, ")", Position(0, 7), Position(0, 9)),
                Token(TokenType.LBRACE, "{", Position(0, 10), Position(0, 11)),
                Token(TokenType.NEW_LINE, "\n", Position(0, 12), Position(1, 0)),
                Token(TokenType.IF_KEYWORD, "if", Position(1, 1), Position(1, 2)),
                Token(TokenType.LPAREN, "(", Position(1, 3), Position(1, 4)),
                Token(TokenType.IDENTIFIER, "b", Position(1, 5), Position(1, 10)),
                Token(TokenType.RPAREN, ")", Position(1, 11), Position(1, 12)),
                Token(TokenType.LBRACE, ";", Position(1, 13), Position(1, 14)),
                Token(TokenType.NEW_LINE, "\n", Position(1, 15), Position(2, 0)),
                Token(TokenType.PRINTLN_FUNCTION, "println", Position(2, 1), Position(2, 2)),
                Token(TokenType.LPAREN, "(", Position(2, 3), Position(2, 4)),
                Token(TokenType.STRING, "Hello", Position(2, 5), Position(2, 10)),
                Token(TokenType.RPAREN, ")", Position(2, 11), Position(2, 12)),
                Token(TokenType.SEMICOLON, ";", Position(2, 13), Position(2, 14)),
                Token(TokenType.NEW_LINE, "\n", Position(2, 15), Position(3, 0)),
                Token(TokenType.RBRACE, "}", Position(3, 1), Position(3, 2)),
                Token(TokenType.NEW_LINE, "\n", Position(3, 3), Position(4, 0)),
                Token(TokenType.RBRACE, "}", Position(4, 1), Position(4, 2)),
            )
        val parser = Parser.getDefaultParser()
        val actualAst = parser.generateAST(tokens)
        val expectedAst =
            Conditional(
                IdentifierOperator("a"),
                listOf(
                    Conditional(
                        IdentifierOperator("b"),
                        listOf(Method("println", StringOperator("Hello"))),
                        null,
                    ),
                ),
                null,
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 034 - const statement`() {
        val tokens =
            listOf(
                Token(TokenType.CONST_KEYWORD, "const", Position(0, 0), Position(0, 5)),
                Token(TokenType.IDENTIFIER, "a", Position(0, 6), Position(0, 7)),
                Token(TokenType.COLON, ":", Position(0, 8), Position(0, 9)),
                Token(TokenType.NUMBER_TYPE, "number", Position(0, 10), Position(0, 16)),
                Token(TokenType.EQ, "=", Position(0, 17), Position(0, 18)),
                Token(TokenType.NUMBER, "5", Position(0, 19), Position(0, 20)),
                Token(TokenType.SEMICOLON, ";", Position(0, 21), Position(0, 22)),
            )
        val parser = Parser.getDefaultParser()
        val actualAst = parser.generateAST(tokens)
        val expectedAst =
            DeclarationAssignation(
                Declaration("a", "number"),
                NumberOperator(5),
                true,
            )
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 035 - declaration statement with readEnv function`() {
        val tokens =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(0, 0), Position(0, 3)),
                Token(TokenType.IDENTIFIER, "home", Position(0, 4), Position(0, 8)),
                Token(TokenType.COLON, ":", Position(0, 9), Position(0, 10)),
                Token(TokenType.STRING_TYPE, "string", Position(0, 11), Position(0, 17)),
                Token(TokenType.EQ, "=", Position(0, 18), Position(0, 19)),
                Token(TokenType.READENV_FUNCTION, "readEnv", Position(0, 20), Position(0, 27)),
                Token(TokenType.LPAREN, "(", Position(0, 28), Position(0, 29)),
                Token(TokenType.STRING, "HOME", Position(0, 30), Position(0, 34)),
                Token(TokenType.RPAREN, ")", Position(0, 35), Position(0, 36)),
                Token(TokenType.SEMICOLON, ";", Position(0, 37), Position(0, 38)),
            )
        val parser = Parser.getDefaultParser()
        val actualAst = parser.generateAST(tokens)
        val expectedAst = DeclarationAssignation(Declaration("home", "string"), Method("readEnv", StringOperator("HOME")), false)
        assertEquals(expectedAst, actualAst)
    }

    @Test
    fun `test 036 - declaration statement with readInput function`() {
        val tokens =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(0, 0), Position(0, 3)),
                Token(TokenType.IDENTIFIER, "home", Position(0, 4), Position(0, 8)),
                Token(TokenType.COLON, ":", Position(0, 9), Position(0, 10)),
                Token(TokenType.STRING_TYPE, "string", Position(0, 11), Position(0, 17)),
                Token(TokenType.EQ, "=", Position(0, 18), Position(0, 19)),
                Token(TokenType.READINPUT_FUNCTION, "readInput", Position(0, 20), Position(0, 27)),
                Token(TokenType.LPAREN, "(", Position(0, 28), Position(0, 29)),
                Token(TokenType.STRING, "Write the name of your home: ", Position(0, 30), Position(0, 34)),
                Token(TokenType.RPAREN, ")", Position(0, 35), Position(0, 36)),
                Token(TokenType.SEMICOLON, ";", Position(0, 37), Position(0, 38)),
            )
        val parser = Parser.getDefaultParser()
        val actualAst = parser.generateAST(tokens)
        val expectedAst = DeclarationAssignation(Declaration("home", "string"), Method("readInput", StringOperator("Write the name of your home: ")), false)
        assertEquals(expectedAst, actualAst)
    }

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
