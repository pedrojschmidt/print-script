import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import java.io.File
import java.io.FileInputStream

class CLI : CliktCommand() {
    private val option: Int by option().int().prompt("Option").help("Number of the operation to perform")
    private val file by option().file(mustExist = true, canBeDir = false).prompt("\nFile path")

    //    private val version by option().prompt("\nVersion").help("Version of the PrintScript")
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
            3 -> {
                echo("\nConfiguration file:")
                val configFilePath = readlnOrNull()
                val configFile = File(configFilePath.toString())
                if (!configFile.exists()) {
                    echo("Configuration file does not exist")
                    return
                }
                formatFile(file, version, configFile)
            }
            4 -> {
                echo("\nConfiguration file:")
                val configFilePath = readlnOrNull()
                val configFile = File(configFilePath.toString())
                if (!configFile.exists()) {
                    echo("Configuration file does not exist")
                    return
                }
                analyzeFile(file, version, configFile)
            }
            else -> echo("Invalid option")
        }
    }

    private fun validateFile(
        file: File,
        version: String,
    ) {
        echo("\nValidating...")

        fillAstList(file)

        echo("File is valid")
    }

    private fun executeFile(
        file: File,
        version: String,
    ) {
        echo("\nExecuting...")
        val astList = fillAstList(file)

        val interpreter = Interpreter()
        val result = interpreter.consume(astList)

        echo(result)
    }

    private fun formatFile(
        file: File,
        version: String,
        configFile: File,
    ) {
        echo("\nFormatting...")
        val astList = fillAstList(file)

        val yamlContent = configFile.readText()
        val formatter = Formatter.fromYaml(yamlContent)
        val formattedAst = formatter.formatString(astList)
        echo(formattedAst)

        file.writeText(formattedAst)
        echo("File formatted successfully")
    }

    private fun analyzeFile(
        file: File,
        version: String,
        configFile: File,
    ) {
        echo("\nAnalyzing...")
        val astList = fillAstList(file)

        val yamlContent = configFile.readText()
        val sca = StaticCodeAnalyzer.fromYaml(yamlContent)
        val scaIssues = sca.analyze(astList)

        if (scaIssues.isNotEmpty()) {
            echo("Problems found by the linter:")
            scaIssues.forEachIndexed { index, issue ->
                echo("${index + 1}. ${issue.message} Line ${issue.position.x}, column ${issue.position.y}")
            }
            echo()
        } else {
            echo("No static analysis problems found.\n")
        }
    }

    private fun fillAstList(file: File): MutableList<ASTNode> {
        val tokenProvider = TokenProvider(FileInputStream(file))
        val parser = Parser.getDefaultParser()
        val astList = mutableListOf<ASTNode>()
        while (tokenProvider.hasNextStatement()) {
            val tokens = tokenProvider.readStatement()
            val ast = parser.generateAST(tokens)
            // Add the AST to the list if it is not null
            ast?.let { astList.add(it) }
        }
        return astList
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
