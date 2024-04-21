import java.io.File

class CommandFactory(
    private val lexer: Lexer,
    private val tokenProvider: TokenProvider,
    private val parser: Parser,
    private val interpreter: Interpreter,
    private val formatter: Formatter,
    private val staticCodeAnalyzer: StaticCodeAnalyzer,
) {
    fun createCommand(
        option: Option,
        file: File,
        configFile: File?,
    ): Command {
        return when (option) {
            Option.VALIDATE -> ValidateCommand(file, lexer, tokenProvider, parser)
            Option.EXECUTE -> ExecuteCommand(file, lexer, tokenProvider, parser, interpreter)
            Option.FORMAT -> FormatCommand(file, configFile, lexer, tokenProvider, parser, formatter)
            Option.ANALYZE -> AnalyzeCommand(file, configFile, lexer, tokenProvider, parser, staticCodeAnalyzer)
        }
    }
}
