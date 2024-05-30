package lexer

import token.Token
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class TokenProvider(input: InputStream, private val lexer: Lexer) {
    private var reader = BufferedReader(InputStreamReader(input))
    private var nextLine: String? = reader.readLine()

    fun readStatement(): List<Token> {
        val tokens = mutableListOf<Token>()
        while (nextLine != null) {
            tokens.addAll(lexer.makeTokens(nextLine!!))
            if (nextLine!!.endsWith(";")) {
                nextLine = reader.readLine()
                break
            }
            nextLine = reader.readLine()
        }
        return tokens
    }

    fun hasNextStatement(): Boolean {
        return nextLine != null
    }
}
