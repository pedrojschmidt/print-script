import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class TokenProvider(private val input: InputStream) {
    private val lexer = Lexer()
    private val reader = BufferedReader(InputStreamReader(input))
    private var hasNextStatement = true

    fun readStatement(): List<Token> {
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
        } else {
            hasNextStatement = false
        }

        return lexer.makeTokens(statement.toString())
    }

    fun hasNextStatement(): Boolean {
        return hasNextStatement
    }
}
