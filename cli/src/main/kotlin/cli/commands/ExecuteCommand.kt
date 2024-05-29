package cli.commands

import Parser
import interpreter.ExecuteInterpreter
import interpreter.response.ErrorResponse
import interpreter.response.SuccessResponse
import lexer.Lexer
import lexer.TokenProvider
import java.io.File
import java.io.FileInputStream

class ExecuteCommand(private val file: File, private val lexer: Lexer, private val parser: Parser, private val interpreter: ExecuteInterpreter) : Command {
    override fun execute() {
        println("Executing...")

        val tokenProvider = TokenProvider(FileInputStream(file), lexer)
        val astList = fillAstListWithProgress(file, parser, tokenProvider)

        when (val result = interpreter.interpretAST(astList)) {
            is SuccessResponse -> {
                println(result.message)
            }
            is ErrorResponse -> {
                println("Error: " + result.message)
            }
            else -> {
                println("Execution completed")
            }
        }
    }
}
