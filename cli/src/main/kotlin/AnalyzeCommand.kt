import java.io.File
import java.io.FileInputStream

class AnalyzeCommand(private val file: File, private val configFile: File?, private val lexer: Lexer, private val parser: Parser, private val staticCodeAnalyzer: StaticCodeAnalyzer) : Command {
    override fun execute() {
        println("Analyzing...")

        val tokenProvider = TokenProvider(FileInputStream(file))
        val astList = fillAstListWithProgress(file, parser, tokenProvider)

        val yamlContent = configFile!!.readText()
        val sca = StaticCodeAnalyzer.fromYaml(yamlContent)
        val scaIssues = sca.analyze(astList)

        if (scaIssues.isNotEmpty()) {
            println("Problems found by the linter:")
            scaIssues.forEachIndexed { index, issue ->
                println("${index + 1}. ${issue.message} Line ${issue.position.x}, column ${issue.position.y}")
            }
            println()
        } else {
            println("No static analysis problems found.\n")
        }
    }
}
