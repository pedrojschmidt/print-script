package lexer

import token.Position
import token.Token
import token.TokenType

class SymbolTokenMaker : TokenMaker {
    override fun makeToken(
        input: String,
        position: Int,
        positionX: Int,
        positionY: Int,
    ): Token? {
        val symbol = input[position]
        return when (symbol) {
            '(' -> Token(TokenType.LPAREN, "(", Position(positionX, positionY), Position(positionX + 1, positionY))
            ')' -> Token(TokenType.RPAREN, ")", Position(positionX, positionY), Position(positionX + 1, positionY))
            '=' -> Token(TokenType.EQ, "=", Position(positionX, positionY), Position(positionX + 1, positionY))
            ':' -> Token(TokenType.COLON, ":", Position(positionX, positionY), Position(positionX + 1, positionY))
            '+' -> Token(TokenType.PLUS, "+", Position(positionX, positionY), Position(positionX + 1, positionY))
            '-' -> Token(TokenType.MINUS, "-", Position(positionX, positionY), Position(positionX + 1, positionY))
            '*' -> Token(TokenType.TIMES, "*", Position(positionX, positionY), Position(positionX + 1, positionY))
            '/' -> Token(TokenType.DIV, "/", Position(positionX, positionY), Position(positionX + 1, positionY))
            ';' -> Token(TokenType.SEMICOLON, ";", Position(positionX, positionY), Position(positionX + 1, positionY))
            else -> null
        }
    }
}
