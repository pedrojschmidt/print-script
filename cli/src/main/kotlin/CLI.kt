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
            1 -> echo("You chose Validation for file: ${file.absolutePath}")
            2 -> echo("You chose Execution for file: ${file.absolutePath}")
            3 -> echo("You chose Formatting for file: ${file.absolutePath}")
            4 -> echo("You chose Analyzing for file: ${file.absolutePath}")
            else -> echo("Invalid option")
        }
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
