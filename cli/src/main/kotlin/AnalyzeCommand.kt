import java.io.File

class AnalyzeCommand(private val file: File, private val configFile: File?, lexer: Lexer, parser: Parser, staticCodeAnalyzer: StaticCodeAnalyzer) : Command {
    override fun execute() {
        println("Analyzed")
    }
}
