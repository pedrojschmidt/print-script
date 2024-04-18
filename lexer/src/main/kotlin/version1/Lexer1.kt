package version1

import Token1
import TokenType1

class Lexer1(private val tokenMakers1: Map<Char, TokenMaker1>) {
    fun makeTokens(inputText: String): List<Token1> {
        var position = 0
        var positionX = 1
        var positionY = 1
        val token1List = mutableListOf<Token1>()

        val input = inputText.replace("\r\n", "\n").replace("\r", "\n")

        while (position < input.length) {
            val currentChar = input[position]

            // Ignore spaces
            if (currentChar == ' ') {
                position++
                positionX++
                continue
            }

            val tokenMaker = tokenMakers1[currentChar]
            if (tokenMaker != null) {
                val token = tokenMaker.makeToken(input, position, positionX, positionY)
                if (token != null) {
                    token1List.add(token)
                    position += token.value.length
                    if (token.type == TokenType1.NEW_LINE) {
                        positionY++
                        positionX = 1
                        continue
                    }
                    positionX += token.value.length
                    // Skip the quotes of strings
                    if (token.type == TokenType1.STRING) {
                        position += 2
                        positionX += 2
                    }
                }
            } else {
                throw Exception("Unrecognized character $currentChar at position $positionX:$positionY")
            }
        }

        return token1List
    }

    companion object {
        fun getDefaultLexer(): Lexer1 {
            return Lexer1(
                ('0'..'9').associateWith { NumberTokenMaker1() } +
                    ('a'..'z').associateWith { IdentifierTokenMaker1() } +
                    ('A'..'Z').associateWith { IdentifierTokenMaker1() } +
                    mapOf(
                        Pair('\"', StringTokenMaker1()),
                        Pair('\'', StringTokenMaker1()),
                        Pair('(', SymbolTokenMaker1()),
                        Pair(')', SymbolTokenMaker1()),
                        Pair('=', SymbolTokenMaker1()),
                        Pair(':', SymbolTokenMaker1()),
                        Pair('+', SymbolTokenMaker1()),
                        Pair('-', SymbolTokenMaker1()),
                        Pair('*', SymbolTokenMaker1()),
                        Pair('/', SymbolTokenMaker1()),
                        Pair(';', SymbolTokenMaker1()),
                        Pair('{', SymbolTokenMaker1()),
                        Pair('}', SymbolTokenMaker1()),
                        Pair('\n', NewLineTokenMaker1()),
                    ),
            )
        }
    }
}
