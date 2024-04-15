package version_0

import Position

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
            "let" -> Token(
                TokenType.LET_KEYWORD,
                "let",
                Position(positionX, positionY),
                Position(positionX + 3, positionY)
            )
            "println" -> Token(
                TokenType.PRINTLN_FUNCTION,
                "println",
                Position(positionX, positionY),
                Position(positionX + 7, positionY)
            )
            "number" -> Token(
                TokenType.NUMBER_TYPE,
                "number",
                Position(positionX, positionY),
                Position(positionX + 6, positionY)
            )
            "string" -> Token(
                TokenType.STRING_TYPE,
                "string",
                Position(positionX, positionY),
                Position(positionX + 6, positionY)
            )
            else -> if (identifier.isNotEmpty()) Token(
                TokenType.IDENTIFIER,
                identifier,
                Position(positionX, positionY),
                Position(positionX + identifier.length, positionY)
            ) else null
        }
    }
}
