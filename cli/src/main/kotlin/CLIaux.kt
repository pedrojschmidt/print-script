import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.file
import java.io.File

class CLIaux(
    private val commandFactory: CommandFactory,
) : CliktCommand() {
    private val option: Option by option().enum<Option>().prompt("Option").help("Number of the operation to perform")
    private val file by option().file(mustExist = true, canBeDir = false).prompt("\nFile path")

    override fun run() {
        var configFile: File? = null
        when (option) {
            Option.FORMAT -> {
                print("Ingrese la ruta del archivo de configuración: ")
                val path = readlnOrNull()
                configFile = File(path!!)
            }
            Option.ANALYZE -> {
                print("Ingrese la ruta del archivo de configuración: ")
                val path = readlnOrNull()
                configFile = File(path!!)
            }
            else -> {}
        }
        val command = commandFactory.createCommand(option, file, configFile)
        command.execute()
    }
}
