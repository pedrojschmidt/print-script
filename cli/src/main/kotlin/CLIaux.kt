import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import java.io.File

class CLIaux(
    private val commandFactory: CommandFactory,
) : CliktCommand() {
    private val option: Int by option().int().prompt("Option").help("Number of the operation to perform")
    private val file by option().file(mustExist = true, canBeDir = false).prompt("\nFile path")

    private var exit = false
    private lateinit var operation: Operation

    override fun run() {
        when (option) {
            1 -> operation = Operation.VALIDATE
            2 -> operation = Operation.EXECUTE
            3 -> operation = Operation.FORMAT
            4 -> operation = Operation.ANALYZE
            5 -> exit = true
            else -> {
                println("Invalid option")
                return
            }
        }

        var configFile: File? = null
        when (operation) {
            Operation.FORMAT -> {
                print("Ingrese la ruta del archivo de configuración: ")
                val path = readlnOrNull()
                configFile = File(path!!)
            }
            Operation.ANALYZE -> {
                print("Ingrese la ruta del archivo de configuración: ")
                val path = readlnOrNull()
                configFile = File(path!!)
            }
            else -> {}
        }
        val command = commandFactory.createCommand(operation, file, configFile)
        command.execute()
    }
}
