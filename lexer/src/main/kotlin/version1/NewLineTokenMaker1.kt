package version1

import Position
import Token1
import TokenType1

class NewLineTokenMaker1 : TokenMaker1 {
    override fun makeToken(
        input: String,
        position: Int,
        positionX: Int,
        positionY: Int,
    ): Token1? {
        return if (input[position] == '\n') {
            Token1(TokenType1.NEW_LINE, "\n", Position(positionX, positionY), Position(positionX, positionY + 1))
        } else {
            null
        }
    }
}
