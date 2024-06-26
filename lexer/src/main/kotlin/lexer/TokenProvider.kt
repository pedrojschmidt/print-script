package lexer

import token.Token
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class TokenProvider(input: InputStream, private val lexer: Lexer) {
    private val reader = BufferedReader(InputStreamReader(input))
    private var currentLine: String? = null

    init {
        currentLine = reader.readLine()
    }

    fun readStatement(): List<Token> {
        if (currentLine == null) return emptyList()

        val statement = StringBuilder()
        var braceCount = 0
        var inIfStatement = false

        while (currentLine != null) {
            val line = currentLine!!.trim()
            statement.append(line).append("\n")

            if (line.startsWith("if")) {
                inIfStatement = true
                braceCount += line.count { it == '{' }
                braceCount -= line.count { it == '}' }
            } else if (inIfStatement) {
                braceCount += line.count { it == '{' }
                braceCount -= line.count { it == '}' }
                if (braceCount == 0) {
                    currentLine = reader.readLine()
                    // Check for an 'else' after closing the 'if' block
                    val nextLine = currentLine?.trim()
                    if (nextLine != null && nextLine.startsWith("else")) {
                        statement.append(nextLine).append("\n")
                        braceCount += nextLine.count { it == '{' }
                        braceCount -= nextLine.count { it == '}' }
                        currentLine = reader.readLine()
                        while (braceCount > 0 && currentLine != null) {
                            val elseLine = currentLine!!.trim()
                            statement.append(elseLine).append("\n")
                            braceCount += elseLine.count { it == '{' }
                            braceCount -= elseLine.count { it == '}' }
                            currentLine = reader.readLine()
                        }
                    }
                    break
                }
            } else if (line.endsWith(";")) {
                currentLine = reader.readLine()
                break
            }
            currentLine = reader.readLine()
        }

        return lexer.makeTokens(statement.toString())
    }

    fun hasNextStatement(): Boolean {
        return currentLine != null
    }
}
