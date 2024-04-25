import java.io.File

class FormatCommand(private val file: File, private val configFile: File?, lexer: Lexer, parser: Parser, formatter: Formatter) : Command {
    override fun execute() {
        println("Formatted")
    }
}
