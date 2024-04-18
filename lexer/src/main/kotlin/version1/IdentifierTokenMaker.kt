package version1

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
            "const" -> Token(
                TokenType.CONST_KEYWORD,
                "const",
                Position(positionX - 5, positionY),
                Position(positionX, positionY)
            )
            "if" -> Token(
                TokenType.IF_FUNCTION,
                "if",
                Position(positionX - 2, positionY),
                Position(positionX, positionY)
            )
            "else" -> Token(
                TokenType.ELSE,
                "else",
                Position(positionX - 4, positionY),
                Position(positionX, positionY)
            )
            "boolean" -> Token(
                TokenType.BOOLEAN_TYPE,
                "boolean",
                Position(positionX - 7, positionY),
                Position(positionX, positionY)
            )
            "true" -> Token(
                TokenType.BOOLEAN,
                "true",
                Position(positionX - 4, positionY),
                Position(positionX, positionY)
            )
            "false" -> Token(
                TokenType.BOOLEAN,
                "false",
                Position(positionX - 5, positionY),
                Position(positionX, positionY)
            )
            "readInput" -> Token(
                TokenType.READINPUT_FUNCTION,
                "readInput",
                Position(positionX - 9, positionY),
                Position(positionX, positionY)
            )
            "readEnv" -> Token(
                TokenType.READENV_FUNCTION,
                "readEnv",
                Position(positionX - 7, positionY),
                Position(positionX, positionY)
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
