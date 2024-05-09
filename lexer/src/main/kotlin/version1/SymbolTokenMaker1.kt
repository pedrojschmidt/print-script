package version1

import Position
import Token1
import TokenType1

class SymbolTokenMaker1 : TokenMaker1 {
    override fun makeToken(
        input: String,
        position: Int,
        positionX: Int,
        positionY: Int,
    ): Token1? {
        val symbol = input[position]
        return when (symbol) {
            '(' -> Token1(TokenType1.LPAREN, "(", Position(positionX, positionY), Position(positionX + 1, positionY))
            ')' -> Token1(TokenType1.RPAREN, ")", Position(positionX, positionY), Position(positionX + 1, positionY))
            '=' -> Token1(TokenType1.EQ, "=", Position(positionX, positionY), Position(positionX + 1, positionY))
            ':' -> Token1(TokenType1.COLON, ":", Position(positionX, positionY), Position(positionX + 1, positionY))
            '+' -> Token1(TokenType1.PLUS, "+", Position(positionX, positionY), Position(positionX + 1, positionY))
            '-' -> Token1(TokenType1.MINUS, "-", Position(positionX, positionY), Position(positionX + 1, positionY))
            '*' -> Token1(TokenType1.TIMES, "*", Position(positionX, positionY), Position(positionX + 1, positionY))
            '/' -> Token1(TokenType1.DIV, "/", Position(positionX, positionY), Position(positionX + 1, positionY))
            ';' -> Token1(TokenType1.SEMICOLON, ";", Position(positionX, positionY), Position(positionX + 1, positionY))
            '{' -> Token1(TokenType1.LKEY, "{", Position(positionX, positionY), Position(positionX + 1, positionY))
            '}' -> Token1(TokenType1.RKEY, "}", Position(positionX, positionY), Position(positionX + 1, positionY))
            else -> null
        }
    }
}
