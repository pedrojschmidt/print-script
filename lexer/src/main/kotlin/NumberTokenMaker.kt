class NumberTokenMaker : TokenMaker {
    override fun makeToken(
        input: String,
        position: Int,
        positionX: Int,
        positionY: Int,
    ): Token? {
        var number = ""
        var pos = position
        while (pos < input.length && input[pos].isDigit()) {
            number += input[pos]
            pos++
        }
        return if (number.isNotEmpty()) Token(TokenType.NUMBER, number, Position(positionX, positionY), Position(positionX + number.length, positionY)) else null
    }
}
