class IdentifierTokenMaker : TokenMaker {
    override fun makeToken(
        input: String,
        position: Int,
        positionX: Int,
        positionY: Int,
    ): Token? {
        var identifier = ""
        var pos = position
        while (pos < input.length && input[pos].isLetter()) {
            identifier += input[pos]
            pos++
        }
        return when (identifier) {
            "let" -> Token(TokenType.LET_KEYWORD, "let", Position(positionX, positionY), Position(positionX + 3, positionY))
            "const" -> Token(TokenType.CONST_KEYWORD, "const", Position(positionX, positionY), Position(positionX + 5, positionY))
            "println" -> Token(TokenType.PRINTLN_FUNCTION, "println", Position(positionX, positionY), Position(positionX + 7, positionY))
            "readInput" -> Token(TokenType.READINPUT_FUNCTION, "readInput", Position(positionX, positionY), Position(positionX + 8, positionY))
            "readEnv" -> Token(TokenType.READENV_FUNCTION, "readEnv", Position(positionX, positionY), Position(positionX + 6, positionY))
            "number" -> Token(TokenType.NUMBER_TYPE, "number", Position(positionX, positionY), Position(positionX + 6, positionY))
            "string" -> Token(TokenType.STRING_TYPE, "string", Position(positionX, positionY), Position(positionX + 6, positionY))
            "boolean" -> Token(TokenType.BOOLEAN_TYPE, "boolean", Position(positionX, positionY), Position(positionX + 7, positionY))
            "if" -> Token(TokenType.IF_KEYWORD, "if", Position(positionX, positionY), Position(positionX + 2, positionY))
            "else" -> Token(TokenType.ELSE_KEYWORD, "else", Position(positionX, positionY), Position(positionX + 4, positionY))
            "true" -> Token(TokenType.BOOLEAN, "true", Position(positionX, positionY), Position(positionX + 4, positionY))
            "false" -> Token(TokenType.BOOLEAN, "false", Position(positionX, positionY), Position(positionX + 5, positionY))
            else -> if (identifier.isNotEmpty()) Token(TokenType.IDENTIFIER, identifier, Position(positionX, positionY), Position(positionX + identifier.length, positionY)) else null
        }
    }
}
