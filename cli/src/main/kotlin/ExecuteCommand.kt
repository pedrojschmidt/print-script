import java.io.File

class ExecuteCommand(private val file: File, lexer: Lexer, parser: Parser, interpreter: Interpreter) : Command {
    override fun execute() {
        // Lógica para ejecutar el archivo
    }
}
