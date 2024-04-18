package version1

import Position
import Token1
import TokenType1

class NumberTokenMaker1 : TokenMaker1 {
    override fun makeToken(
        input: String,
        position: Int,
        positionX: Int,
        positionY: Int,
    ): Token1? {
        var number = ""
        var pos = position
        var decimalPointEncountered = false
        while (pos < input.length && input[pos].isDigit() || input[pos] == '.') {
            if (input[pos] == '.') {
                if (decimalPointEncountered) {
                    // Ya se encontró un punto decimal en este número, por lo que no se puede agregar otro
                    throw RuntimeException("Invalid number format at position $positionX:$positionY")
                } else {
                    decimalPointEncountered = true
                }
            }
            number += input[pos]
            pos++
        }
        return if (number.isNotEmpty()) {
            Token1(
                TokenType1.NUMBER,
                number,
                Position(positionX, positionY),
                Position(positionX + number.length, positionY),
            )
        } else {
            null
        }
    }
}
