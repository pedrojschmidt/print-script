package version1

import Position
import Token1
import TokenType1

class IdentifierTokenMaker1 : TokenMaker1 {
    override fun makeToken(
        input: String,
        position: Int,
        positionX: Int,
        positionY: Int,
    ): Token1? {
        var identifier = ""
        var pos = position
        while (pos < input.length && input[pos].isLetter()) {
            identifier += input[pos]
            pos++
        }
        return when (identifier) {
            "let" ->
                Token1(TokenType1.LET_KEYWORD, "let", Position(positionX, positionY), Position(positionX + 3, positionY))
            "println" ->
                Token1(TokenType1.PRINTLN_FUNCTION, "println", Position(positionX, positionY), Position(positionX + 7, positionY))
            "number" ->
                Token1(TokenType1.NUMBER_TYPE, "number", Position(positionX, positionY), Position(positionX + 6, positionY))
            "string" ->
                Token1(TokenType1.STRING_TYPE, "string", Position(positionX, positionY), Position(positionX + 6, positionY))
            "const" ->
                Token1(TokenType1.CONST_KEYWORD, "const", Position(positionX - 5, positionY), Position(positionX, positionY))
            "if" ->
                Token1(TokenType1.IF_FUNCTION, "if", Position(positionX - 2, positionY), Position(positionX, positionY))
            "else" ->
                Token1(TokenType1.ELSE, "else", Position(positionX - 4, positionY), Position(positionX, positionY))
            "boolean" ->
                Token1(TokenType1.BOOLEAN_TYPE, "boolean", Position(positionX - 7, positionY), Position(positionX, positionY))
            "true" ->
                Token1(TokenType1.BOOLEAN, "true", Position(positionX - 4, positionY), Position(positionX, positionY))
            "false" ->
                Token1(TokenType1.BOOLEAN, "false", Position(positionX - 5, positionY), Position(positionX, positionY))
            "readInput" ->
                Token1(TokenType1.READINPUT_FUNCTION, "readInput", Position(positionX - 9, positionY), Position(positionX, positionY))
            "readEnv" ->
                Token1(TokenType1.READENV_FUNCTION, "readEnv", Position(positionX - 7, positionY), Position(positionX, positionY))
            else ->
                if (identifier.isNotEmpty()) {
                    Token1(TokenType1.IDENTIFIER, identifier, Position(positionX, positionY), Position(positionX + identifier.length, positionY))
                } else {
                    null
                }
        }
    }
}
