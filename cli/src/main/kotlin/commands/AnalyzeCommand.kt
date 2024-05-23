package commands

import Parser
import lexer.Lexer
import lexer.TokenProvider
import sca.ExecuteSca
import sca.ScaFactory
import sca.StaticCodeAnalyzerRules
import sca.StaticCodeIssue
import java.io.File
import java.io.FileInputStream

class AnalyzeCommand(private val file: File, private val configFilePath: String, private val lexer: Lexer, private val parser: Parser, private val staticCodeAnalyzer: ExecuteSca) : Command {
    override fun execute() {
        println("Analyzing...")

        val tokenProvider = TokenProvider(FileInputStream(file), lexer)
        val astList = fillAstListWithProgress(file, parser, tokenProvider)

        val sca = ExecuteSca()
        val scaIssues: MutableList<StaticCodeIssue> = mutableListOf()
        for (ast in astList) {
            scaIssues += sca.analyzeNode(ast, StaticCodeAnalyzerRules(configFilePath), ScaFactory().assignAnalyzers())
        }

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
