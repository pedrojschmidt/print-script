import java.io.File

class ValidateCommand(private val file: File, lexer: Lexer, parser: Parser) : Command {
    override fun execute() {
        println("Validated")
    }
}
