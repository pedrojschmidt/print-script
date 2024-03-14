class Parser(private val tokens: List<Token>) {

    private var currentTokenIndex = 0

    fun generateAST(): List<ASTNode> {
        // Crear una lista mutable para almacenar los nodos de las declaraciones
        val astNodes = mutableListOf<ASTNode>()
        while (currentTokenIndex < tokens.size) {
            val currentToken = tokens[currentTokenIndex]

            when (currentToken.type) {
                TokenType.LET_KEYWORD -> {
                    astNodes.add(parseDeclarationAssignation())
                }
                TokenType.PRINTLN_FUNCTION -> {
                    astNodes.add(parsePrintlnStatement())
                }
                // Agregar más casos (como asignaciones, operaciones, etc.)
                else -> {
                    throw RuntimeException("Token de tipo ${currentToken.type} inesperado en la línea ${currentToken.positionStart.x}:${currentToken.positionStart.y}")
                }
            }
//            currentTokenIndex++
        }
        return astNodes
    }

    private fun parseDeclarationAssignation(): DeclarationAssignation {
        // Consumir el token de "let", devuelve el Token
        consume(TokenType.LET_KEYWORD)
        // Consumir el token del identificador, devuelve el Token
        val identifierToken = consume(TokenType.IDENTIFIER)
        consume(TokenType.COLON)

        val type = if (getCurrentToken().type == TokenType.NUMBER_TYPE) {
            consume(TokenType.NUMBER_TYPE)
        } else {
            consume(TokenType.STRING_TYPE)
        }

        consume(TokenType.EQ)
        // Parsear la expresión a la derecha del signo de igual
        val expression = parseContent()
        consume(TokenType.SEMICOLON)

        return DeclarationAssignation(Declaration(identifierToken.value, type.value),  expression)
    }

    private fun parsePrintlnStatement(): Method {
        val identifier = consume(TokenType.PRINTLN_FUNCTION)
        consume(TokenType.LPAREN)

        val content = parseContent()

        consume(TokenType.RPAREN)
        consume(TokenType.SEMICOLON)

        // No se si debe tener una expresion o un identificador
        return Method(identifier.value, content)
    }

    // TODO: Falta agregar la opcion de que haya una "operacion", es decir una composicion de tokens
    private fun parseContent(): BinaryNode {
        val currentToken = getCurrentToken()
        currentTokenIndex++
        return when (currentToken.type) {
            TokenType.STRING -> {
                StringOperator(currentToken.value)
            }
            TokenType.NUMBER -> {
                NumberOperator(currentToken.value.toDouble())
            }
            TokenType.IDENTIFIER -> {
                IdentifierOperator(currentToken.value)
            }
            else -> {
                throw RuntimeException("Token de tipo ${currentToken.type} inesperado en la línea ${currentToken.positionStart.x}:${currentToken.positionStart.y}")
            }
        }
    }

    private fun consume(type: TokenType): Token {
        val currentToken = getCurrentToken()
        val currentTokenType = currentToken.type
        if (currentTokenType == type) {
            currentTokenIndex++
            return currentToken
        } else {
            throw RuntimeException("Se esperaba un token de tipo $type pero fue de tipo $currentTokenType en la línea ${currentToken.positionStart.x}:${currentToken.positionStart.y}")
        }
    }

    private fun getCurrentToken(): Token {
        return tokens[currentTokenIndex]
    }

}
