package commands

import ExecuteInterpreter
import Lexer
import Parser
import TokenProvider
import java.io.File
import java.io.FileInputStream

class ExecuteCommand(private val file: File, private val lexer: Lexer, private val parser: Parser, private val interpreter: ExecuteInterpreter) : Command {
    override fun execute() {
        println("Executing...")

        val tokenProvider = TokenProvider(FileInputStream(file), lexer)

        val astList = fillAstListWithProgress(file, parser, tokenProvider)
        val result = interpreter.interpretAST(astList)

        println(result)
    }
}
