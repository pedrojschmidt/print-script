import java.io.File
import java.io.FileInputStream

class ExecuteCommand(private val file: File, private val lexer: Lexer, private val parser: Parser, private val interpreter: Interpreter) : Command {
    override fun execute() {
        println("Executing...")

        val tokenProvider = TokenProvider(FileInputStream(file))

        val astList = fillAstListWithProgress(file, parser, tokenProvider)
        val result = interpreter.interpretAST(astList)

        println(result)
    }
}
