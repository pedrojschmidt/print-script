import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import java.io.File

class CLI : CliktCommand() {
    private val option: Int by option().int().prompt("Option").help("Number of the operation to perform")
    private val file by option().file(mustExist = true, canBeDir = false).prompt("\nPlease enter the file path")

//    private val version by option().prompt("Version").help("Version of the PrintScript")
    private val version = "1.0"

    private val validVersions = listOf("1.0")

    override fun run() {
        if (version !in validVersions) {
            echo("Invalid version")
            return
        }
        when (option) {
            1 -> validateFile(file, version)
            2 -> executeFile(file, version)
            3 -> formatFile(file, version)
            4 -> analyzeFile(file, version)
            else -> echo("Invalid option")
        }
    }

    private fun validateFile(
        file: File,
        version: String,
    ) {
        echo("\nValidating...")
    }

    private fun executeFile(
        file: File,
        version: String,
    ) {
        echo("\nExecuting...")
        val code = file.readText()

        val lexer = Lexer(code)
        val tokens = lexer.makeTokens()

        val parser = Parser(tokens)
        val ast = parser.generateAST()

        val interpreter = Interpreter()
        val result = interpreter.consume(ast)

        echo(result)
    }

    private fun formatFile(
        file: File,
        version: String,
    ) {
        echo("\nFormatting...")
    }

    private fun analyzeFile(
        file: File,
        version: String,
    ) {
        echo("\nAnalyzing...")
    }
}

fun main(args: Array<String>) {
    println(
        """
| ------- Wellcome to PrintScript 1.0 CLI -------
|
| Choose one of the following options:
|
|   1. Validation
|   2. Execution
|   3. Formatting
|   4. Analyzing
|
    """,
    )
    CLI().main(args)
}
