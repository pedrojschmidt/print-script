import java.io.FileInputStream

fun main(args: Array<String>) {
    println(
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

Version:
    """,
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

//    val lexer = Lexer.getLexerByVersion(version)
//    val parser = Parser.getParserByVersion(version)
//    val interpreter = Interpreter.getInterpreterByVersion(version)
//    val formatter = Formatter.getFormatterByVersion(version)
//    val staticCodeAnalyzer = StaticCodeAnalyzer.getSCAByVersion(version)

    val lexer = Lexer.getDefaultLexer()
    val tokenProvider = TokenProvider(FileInputStream("file"))
    val parser = Parser.getDefaultParser()
    val interpreter = Interpreter()
    val formatter = Formatter.fromDefault()
    val staticCodeAnalyzer = StaticCodeAnalyzer.fromYaml("")

    println(
        """
| ------- PrintScript $version CLI -------
|
| Choose one of the following options:
|
|   1. Validation
|   2. Execution
|   3. Formatting
|   4. Analyzing
|   5. Exit
|
    """,
    )

    CLIaux(
        CommandFactory(
            lexer,
            tokenProvider,
            parser,
            interpreter,
            formatter,
            staticCodeAnalyzer,
        ),
    ).main(args)
}
