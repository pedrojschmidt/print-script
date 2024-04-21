import java.io.File

class FormatCommand(private val file: File, private val configFile: File?, lexer: Lexer, tokenProvider: TokenProvider, parser: Parser, formatter: Formatter) : Command {
    override fun execute() {
        // Lógica para formatear el archivo
    }
}
