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

    private fun parseDeclarationAssignation(): ASTNode {
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

        // Se fija si es de tipo DeclarationAssignation o Declaration
        if (getCurrentToken().type == TokenType.EQ) {
            consume(TokenType.EQ)
            // Parsear la expresión a la derecha del signo de igual
            val expression = parseContent()
            if(getCurrentToken().type == TokenType.RPAREN){
                consume(TokenType.RPAREN)
                consume(TokenType.SEMICOLON)
                return DeclarationAssignation(Declaration(identifierToken.value, type.value),  expression)
            }else{
                consume(TokenType.SEMICOLON)
                return DeclarationAssignation(Declaration(identifierToken.value, type.value),  expression)
            }
        } else {
            consume(TokenType.SEMICOLON)
            return Declaration(identifierToken.value, type.value)
        }
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
        // let x: number = 5 + 5;
        // println(5 + 5);
        if(getCurrentToken().type == TokenType.LPAREN){
            consume(TokenType.LPAREN)
        }
        val currentToken = getCurrentToken()
        currentTokenIndex++
        val nextToken = getCurrentToken()
        // En el caso de que se pueda usar parentesis en una operacion habria que cambiarlo. Ejemplo: let x: number = 5 + (2 + 5);
        if (nextToken.type == TokenType.SEMICOLON || nextToken.type == TokenType.RPAREN) {
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
        } else {
            // Posibles casos

            // let x: number = 5 + 5;
            // let x: number = 5 + a;
            // let x: number = a + 5;
            // let x: number = a + b;

            // let x: string = "Hello" + " world";
            // let x: string = "Hello" + 5;
            // let x: string = "Hello" + a;
            // let x: string = 5 + "Hello";
            // let x: string = 5 + a;
            // let x: string = a + 5;
            // let x: string = a + "Hello";

            // Sirve para saltear el operador (+ - * /) en la proxima iteracion
            currentTokenIndex++

            return when (currentToken.type) {
                TokenType.STRING -> {
                    BinaryOperation(StringOperator(currentToken.value), nextToken.value, parseContent())
                }
                TokenType.NUMBER -> {
                    BinaryOperation(NumberOperator(currentToken.value.toDouble()), nextToken.value, parseContent())
                }
                TokenType.IDENTIFIER -> {
                    BinaryOperation(IdentifierOperator(currentToken.value), nextToken.value, parseContent())
                }
                else -> {
                    throw RuntimeException("Token de tipo ${currentToken.type} inesperado en la línea ${currentToken.positionStart.x}:${currentToken.positionStart.y}")
                }
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
