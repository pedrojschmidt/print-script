import formatter.ExecuteFormatter
import sca.ExecuteSca

fun main(args: Array<String>) {
    print(
        """
| ------- Welcome to PrintScript CLI -------
|
| Insert the PrintScript version you want to use.
| 
| Available versions:
|
|  - 1.0
|  - 1.1
|

Version: """,
    )

    var version = readlnOrNull()

    while (
        version != "1.0" &&
        version != "1.1"
    ) {
        println("Invalid version. Please insert a valid version.")
        println("")
        println("Version:")
        version = readlnOrNull()
    }

    val lexer = Lexer.getLexerByVersion(version)
    val parser = Parser.getParserByVersion(version)
    val interpreter = ExecuteInterpreter.getInterpreterByVersion(version)
    val formatter = ExecuteFormatter.getFormatterByVersion(version)
    val staticCodeAnalyzer = ExecuteSca.getSCAByVersion(version)

    val optionsStr =
        """
| ------- PrintScript $version CLI -------
|
| Choose one of the following options:
|
|   1. Validation
|   2. Execution
|   3. Formatting
|   4. Analyzing
|

Option"""

    CLI(
        CommandFactory(
            lexer,
            parser,
            interpreter,
            formatter,
            staticCodeAnalyzer,
        ),
        optionsStr,
    ).main(args)
}
