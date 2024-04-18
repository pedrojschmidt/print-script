package version1

import Position
import Token1
import TokenType1

class StringTokenMaker1 : TokenMaker1 {
    override fun makeToken(
        input: String,
        position: Int,
        positionX: Int,
        positionY: Int,
    ): Token1? {
        var str = ""
        var pos = position

        // Guarda el tipo de comillas y las saltea
        val quoteType = input[pos]
        pos++

        // Termina cuando encuentra el mismo tipo de comillas
        while (pos < input.length && input[pos] != quoteType) {
            str += input[pos]
            pos++
        }

        return Token1(
            TokenType1.STRING,
            str,
            Position(positionX, positionY),
            Position(positionX + str.length + 2, positionY),
        )
    }
}
