import commands.AnalyzeCommand
import commands.Command
import commands.ExecuteCommand
import commands.FormatCommand
import formatter.ExecuteFormatter
import lexer.Lexer
import sca.ExecuteSca
import java.io.File

class CommandFactory(
    private val lexer: Lexer,
    private val parser: Parser,
    private val interpreter: ExecuteInterpreter,
    private val formatter: ExecuteFormatter,
    private val staticCodeAnalyzer: ExecuteSca,
) {
    fun createCommand(
        operation: Operation,
        file: File,
        configFile: String,
    ): Command {
        return when (operation) {
            Operation.VALIDATE -> ValidateCommand(file, lexer, parser)
            Operation.EXECUTE -> ExecuteCommand(file, lexer, parser, interpreter)
            Operation.FORMAT -> FormatCommand(file, configFile, lexer, parser, formatter)
            Operation.ANALYZE -> AnalyzeCommand(file, configFile, lexer, parser, staticCodeAnalyzer)
        }
    }

    fun getOperation(option: Int): Operation? {
        return when (option) {
            1 -> Operation.VALIDATE
            2 -> Operation.EXECUTE
            3 -> Operation.FORMAT
            4 -> Operation.ANALYZE
            else -> {
                println("Invalid option")
                null
            }
        }
    }
}
