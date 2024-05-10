package commands

import Lexer
import Parser
import TokenProvider
import formatter.ExecuteFormatter
import formatter.FormatRules
import formatter.FormatterFactory
import java.io.File
import java.io.FileInputStream

class FormatCommand(private val file: File, private val configFilePath: String, private val lexer: Lexer, private val parser: Parser, private val formatter: ExecuteFormatter) :
    Command {
    override fun execute() {
        println("Formatting...")

        val tokenProvider = TokenProvider(FileInputStream(file))
        val astList = fillAstListWithProgress(file, parser, tokenProvider)

        var formattedAst = ""
        for (ast in astList) {
            formattedAst += formatter.formatNode(ast, FormatRules(configFilePath), FormatterFactory().assignFormatters())
        }

        println(formattedAst)

        file.writeText(formattedAst)

        println("File formatted successfully")
    }
}
