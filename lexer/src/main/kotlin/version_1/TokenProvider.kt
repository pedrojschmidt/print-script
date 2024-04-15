package version_1

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class TokenProvider(private val input: InputStream) {
    fun readStatement(): List<Token> {
        val reader = BufferedReader(InputStreamReader(input))
        val statement = StringBuilder()
        var char = reader.read()

        // Read characters until a semicolon is found
        while (char != -1 && char.toChar() != ';') {
            statement.append(char.toChar())
            char = reader.read()
        }

        // If a semicolon is found, append it to the statement
        if (char != -1) {
            statement.append(';')
        }

        // Create a Version_1.Lexer and generate tokens from the statement
        val lexer = Lexer_1()
        return lexer.makeTokens(statement.toString())
    }
}
