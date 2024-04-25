import java.io.File
import java.io.FileInputStream

class ValidateCommand(private val file: File, private val lexer: Lexer, private val parser: Parser) : Command {
    override fun execute() {
        println("Validating...")
        val tokenProvider = TokenProvider(FileInputStream(file))
        fillAstListWithProgress(file, parser, tokenProvider)
        println("File is valid")
    }
}
