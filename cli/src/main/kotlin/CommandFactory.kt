import java.io.File

class CommandFactory(
    private val lexer: Lexer,
    private val parser: Parser,
    private val interpreter: Interpreter,
    private val formatter: Formatter,
    private val staticCodeAnalyzer: StaticCodeAnalyzer,
) {
    fun createCommand(
        operation: Operation,
        file: File,
        configFile: File?,
    ): Command {
        return when (operation) {
//            Option.VALIDATE -> ValidateCommand(file, lexer, tokenProvider, parser)
//            Option.EXECUTE -> ExecuteCommand(file, lexer, tokenProvider, parser, interpreter)
//            Option.FORMAT -> FormatCommand(file, configFile, lexer, tokenProvider, parser, formatter)
//            Option.ANALYZE -> AnalyzeCommand(file, configFile, lexer, tokenProvider, parser, staticCodeAnalyzer)
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
