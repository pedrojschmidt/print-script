import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.io.FileInputStream
import kotlin.math.ceil

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

        fillAstListWithProgress(file)

        echo("File is valid")
    }

    private fun executeFile(
        file: File,
        version: String,
    ) {
        echo("\nExecuting...")
        val astList = fillAstListWithProgress(file)

        val interpreter = Interpreter()
        val result = interpreter.interpretAST(astList)

        echo(result)
    }

    private fun formatFile(
        file: File,
        version: String,
        configFile: File,
    ) {
        echo("\nFormatting...")
        val astList = fillAstListWithProgress(file)

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
        val astList = fillAstListWithProgress(file)

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

    private fun fillAstListWithProgress(file: File): MutableList<ASTNode> {
        val tokenProvider = TokenProvider(FileInputStream(file))
        val parser = Parser.getDefaultParser()
        val astList = mutableListOf<ASTNode>()

        val totalLines = file.readLines().size
        val progressInterval = totalLines / 10 // Actualizar la barra de progreso cada 10%

        var linesProcessed = 0
        var progressCounter = 0

        // Mutex para evitar condiciones de carrera al actualizar el progreso
        val progressMutex = Mutex()

        runBlocking {
            launch(Dispatchers.IO) {
                while (tokenProvider.hasNextStatement()) {
                    val tokens = tokenProvider.readStatement()
                    val ast = parser.generateAST(tokens)
                    ast?.let {
                        // Incrementar el contador de líneas procesadas
                        linesProcessed++
                        // Incrementar el contador de progreso
                        progressCounter++
                        // Actualizar la barra de progreso si es necesario
                        if (progressCounter >= progressInterval || linesProcessed == totalLines) {
                            // Bloquear el mutex antes de actualizar el progreso
                            progressMutex.withLock {
                                val progress = (linesProcessed.toDouble() / totalLines.toDouble()) * 100
                                printProgress(progress)
                                // Reiniciar el contador de progreso
                                progressCounter = 0
                            }
                        }
                        astList.add(it)
                    }
                }
            }
        }

        return astList
    }

    private fun printProgress(progress: Double) {
        // Limpiar la línea anterior antes de imprimir el progreso actualizado
        print("\r" + " ".repeat(50) + "\r")
        // Calcular el número de bloques llenos y vacíos para la barra de progreso
        val numBlocksFilled = ceil(progress / 2).toInt()
        val numBlocksEmpty = 50 - numBlocksFilled
        // Imprimir la barra de progreso
        print("[" + "#".repeat(numBlocksFilled) + "-".repeat(numBlocksEmpty) + "]  ${progress.toInt()}% \n")
    }
}

fun main(args: Array<String>) {
    println(
        """
| ------- Welcome to PrintScript 1.0 CLI -------
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
