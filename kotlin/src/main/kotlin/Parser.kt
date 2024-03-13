class Parser(private val tokens: List<Token>) {

    private var currentTokenIndex = 0

    fun generateAST(): List<ASTNode> {
        // Crear una lista mutable para almacenar los nodos de las declaraciones
        val astNodes = mutableListOf<ASTNode>()
        while (currentTokenIndex < tokens.size) {
            val currentToken = tokens[currentTokenIndex]

            when (currentToken.getType()) {
                TokenType.LET_KEYWORD -> {
                    astNodes.add(parseVariableDeclaration())
                }
                TokenType.PRINTLN_FUNCTION -> {
                    astNodes.add(parsePrintlnStatement())
                }
                // Agregar más casos (como asignaciones, operaciones, etc.)
                else -> {
                    throw RuntimeException("Token de tipo ${currentToken.getType()} inesperado en la línea ${currentToken.getPositionStart().x}:${currentToken.getPositionStart().y}")
                }
            }
//            currentTokenIndex++
        }
        return astNodes
    }

    private fun parseVariableDeclaration(): DeclarationAssignation {
        // Consumir el token de "let", devuelve el Token
        consume(TokenType.LET_KEYWORD)
        // Consumir el token del identificador, devuelve el Token
        val identifierToken = consume(TokenType.IDENTIFIER)
        consume(TokenType.COLON)

        val type = if (getCurrentToken().getType() == TokenType.NUMBER_TYPE) {
            consume(TokenType.NUMBER_TYPE)
        } else {
            consume(TokenType.STRING_TYPE)
        }

        consume(TokenType.EQ)
        // Parsear la expresión a la derecha del signo de igual
        val expression = parseContent()
        consume(TokenType.SEMICOLON)

        return DeclarationAssignation(Declaration(identifierToken, type),  expression)
    }

    private fun parsePrintlnStatement(): Method {
        val identifier = consume(TokenType.PRINTLN_FUNCTION)
        consume(TokenType.LPAREN)

        val content = parseContent()

        consume(TokenType.RPAREN)
        consume(TokenType.SEMICOLON)

        // No se si debe tener una expresion o un identificador
        return Method(identifier, content)
    }

    private fun parseContent(): Literal {
        val currentToken = getCurrentToken()
        currentTokenIndex++
        return Literal(currentToken)
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
