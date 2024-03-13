class Parser(private val tokens: List<Token>) {

    private var currentTokenIndex = 0

    fun generateAST(): RootNode {
        // Crear una lista mutable para almacenar los nodos de las declaraciones
        val statements = mutableListOf<StatementNode>()
        while (currentTokenIndex < tokens.size) {
            val currentToken = tokens[currentTokenIndex]

            when (currentToken.getType()) {
                TokenType.LET_KEYWORD -> {
                    statements.add(parseVariableDeclaration())
                }
                TokenType.PRINTLN_FUNCTION -> {
                    statements.add(parsePrintlnStatement())
                }
                // Agregar más casos (como asignaciones, operaciones, etc.)
                else -> {
                    throw RuntimeException("Token de tipo ${currentToken.getType()} inesperado en la línea ${currentToken.getPositionStart().x}:${currentToken.getPositionStart().y}")
                }
            }
//            currentTokenIndex++
        }

        // Retornar el nodo raíz del árbol
        return RootNode(statements)
    }

    private fun parseVariableDeclaration(): StatementNode {
        // Consumir el token de "let", devuelve el Token
        consume(TokenType.LET_KEYWORD)
        // Consumir el token del identificador, devuelve el Token
        val identifierToken = consume(TokenType.IDENTIFIER)
        val identifier = IdentifierNode(identifierToken.getValue())
        consume(TokenType.COLON)

        if (getCurrentToken().getType() == TokenType.NUMBER_TYPE) {
            consume(TokenType.NUMBER_TYPE)
        } else {
            consume(TokenType.STRING_TYPE)
        }

        consume(TokenType.EQ)
        // Parsear la expresión a la derecha del signo de igual
        val expression = parseExpression()
        consume(TokenType.SEMICOLON)

        return AssignmentNode(identifier, expression)
    }

    private fun parsePrintlnStatement(): StatementNode {
        consume(TokenType.PRINTLN_FUNCTION)
        consume(TokenType.LPAREN)

        val content = parseContent()

        consume(TokenType.RPAREN)
        consume(TokenType.SEMICOLON)

        // No se si debe tener una expresion o un identificador
        return PrintlnNode(content)
    }

    private fun parseExpression(): ExpressionNode {
        val currentToken = getCurrentToken()
        currentTokenIndex++
        return when (currentToken.getType()) {
            TokenType.STRING -> StringNode(currentToken.getValue())
            TokenType.NUMBER -> NumberNode(currentToken.getValue().toDouble())
            else -> throw RuntimeException("Token de tipo ${currentToken.getType()} inesperado en la línea ${currentToken.getPositionStart().x}:${currentToken.getPositionStart().y}")
        }
    }

    private fun parseContent(): ASTNode {
        val currentToken = getCurrentToken()
        currentTokenIndex++
        return when (currentToken.getType()) {
            TokenType.STRING -> StringNode(currentToken.getValue())
            TokenType.NUMBER -> NumberNode(currentToken.getValue().toDouble())
            TokenType.IDENTIFIER -> IdentifierNode(currentToken.getValue())
            else -> throw RuntimeException("Token de tipo ${currentToken.getType()} inesperado en la línea ${currentToken.getPositionStart().x}:${currentToken.getPositionStart().y}")
        }
    }

    private fun consume(type: TokenType): Token {
        val currentToken = getCurrentToken()
        val currentTokenType = currentToken.getType()
        if (currentTokenType == type) {
            currentTokenIndex++
            return currentToken
        } else {
            throw RuntimeException("Se esperaba un token de tipo $type pero fue de tipo $currentTokenType en la línea ${currentToken.getPositionStart().x}:${currentToken.getPositionStart().y}")
        }
    }

    private fun getCurrentToken(): Token {
        return tokens[currentTokenIndex]
    }

}
