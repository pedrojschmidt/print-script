import java.io.File
import java.io.FileInputStream

class FormatCommand(private val file: File, private val configFile: File?, private val lexer: Lexer, private val parser: Parser, private val formatter: Formatter) : Command {
    override fun execute() {
        println("Formatting...")

        val tokenProvider = TokenProvider(FileInputStream(file))
        val astList = fillAstListWithProgress(file, parser, tokenProvider)
        val formatter: Formatter

        if (configFile == null || !configFile.exists()) {
            formatter = Formatter.fromDefault()
        } else {
            formatter = Formatter.fromYaml(configFile.readText())
        }
        val formattedAst = formatter.formatString(astList)
        println(formattedAst)

        file.writeText(formattedAst)

        println("File formatted successfully")
    }
}
