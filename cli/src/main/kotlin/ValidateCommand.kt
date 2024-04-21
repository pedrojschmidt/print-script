import java.io.File

class ValidateCommand(private val file: File, lexer: Lexer, tokenProvider: TokenProvider, parser: Parser) : Command {
    override fun execute() {
        // Lógica para validar el archivo
    }
}
