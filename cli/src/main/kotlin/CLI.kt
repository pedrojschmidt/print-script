import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int

class CLI : CliktCommand() {
    val option: Int by option().int().prompt("    Option").help("Number of the operation to perform")
    val file by option().file(mustExist = true, canBeDir = false).prompt("    Please enter the file path")

    override fun run() {
        when (option) {
            1 -> validateFile(file)
            2 -> executeFile(file)
            3 -> formatFile(file)
            4 -> analyzeFile(file)
            else -> echo("Invalid option")
        }
    }

    private fun validateFile(file: java.io.File) {
        echo("Validating: ${file.absolutePath}")
    }

    private fun executeFile(file: java.io.File) {
        echo("Executing: ${file.absolutePath}")
    }

    private fun formatFile(file: java.io.File) {
        echo("Formatting: ${file.absolutePath}")
    }

    private fun analyzeFile(file: java.io.File) {
        echo("Analyzing: ${file.absolutePath}")
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
