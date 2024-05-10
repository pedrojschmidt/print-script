package tokenmakers

import Position
import Token
import TokenType

class NewLineTokenMaker : TokenMaker {
    override fun makeToken(
        input: String,
        position: Int,
        positionX: Int,
        positionY: Int,
    ): Token? {
        return if (input[position] == '\n') {
            Token(TokenType.NEW_LINE, "\n", Position(positionX, positionY), Position(positionX, positionY + 1))
        } else {
            null
        }
    }
}
