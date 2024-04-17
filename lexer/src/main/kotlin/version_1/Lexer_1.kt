package version_1

class Lexer_1(private val tokenMakers: Map<Char, TokenMaker>) {
    fun makeTokens(inputText: String): List<Token> {
        var position = 0
        var positionX = 1
        var positionY = 1
        val tokenList = mutableListOf<Token>()

        val input = inputText.replace("\r\n", "\n").replace("\r", "\n")

        while (position < input.length) {
            val currentChar = input[position]

            // Ignore spaces
            if (currentChar == ' ') {
                position++
                positionX++
                continue
            }

            val tokenMaker = tokenMakers[currentChar]
            if (tokenMaker != null) {
                val token = tokenMaker.makeToken(input, position, positionX, positionY)
                if (token != null) {
                    tokenList.add(token)
                    position += token.value.length
                    if (token.type == TokenType.NEW_LINE) {
                        positionY++
                        positionX = 1
                        continue
                    }
                    positionX += token.value.length
                    // Skip the quotes of strings
                    if (token.type == TokenType.STRING) {
                        position += 2
                        positionX += 2
                    }
                }
            } else {
                throw Exception("Unrecognized character $currentChar at position $positionX:$positionY")
            }
        }

        return tokenList
    }

    companion object {
        fun getDefaultLexer(): Lexer_1 {
            return Lexer_1(
                ('0'..'9').associateWith { NumberTokenMaker() } +
                    ('a'..'z').associateWith { IdentifierTokenMaker() } +
                    ('A'..'Z').associateWith { IdentifierTokenMaker() } +
                    mapOf(
                        Pair('\"', StringTokenMaker()),
                        Pair('\'', StringTokenMaker()),
                        Pair('(', SymbolTokenMaker()),
                        Pair(')', SymbolTokenMaker()),
                        Pair('=', SymbolTokenMaker()),
                        Pair(':', SymbolTokenMaker()),
                        Pair('+', SymbolTokenMaker()),
                        Pair('-', SymbolTokenMaker()),
                        Pair('*', SymbolTokenMaker()),
                        Pair('/', SymbolTokenMaker()),
                        Pair(';', SymbolTokenMaker()),
                        Pair('{', SymbolTokenMaker()),
                        Pair('}', SymbolTokenMaker()),
                        Pair('\n', NewLineTokenMaker()),
                    ),
            )
        }
    }
}
