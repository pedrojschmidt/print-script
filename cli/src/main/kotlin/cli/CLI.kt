import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int

class CLI(
    private val commandFactory: CommandFactory,
    optionsStr: String,
) : CliktCommand() {
    private val file by option().file(mustExist = true, canBeDir = false).prompt("\nFile path")
    private val option: Int by option().int().prompt(optionsStr).help("Number of the operation to perform")

    private lateinit var operation: Operation

    override fun run() {
        operation = commandFactory.getOperation(option) ?: return

        var configFilePath = ""
        when (operation) {
            Operation.FORMAT, Operation.ANALYZE -> {
                print("Insert configuration file path: ")
                configFilePath = readlnOrNull().toString()
            }
            else -> {}
        }
        val command = commandFactory.createCommand(operation, file, configFilePath)
        command.execute()
    }
}
