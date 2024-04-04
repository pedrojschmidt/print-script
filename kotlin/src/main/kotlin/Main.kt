import java.io.File

fun main() {
    // git hooks commit test
    val example =
        "let a: string = \"hello\";" +
            "println(a);"
    println("Codigo antes del formatter: \n$example\n")

    // El LEXER toma un string y lo convierte en una lista de tokens
    val lexer = Lexer(example)
    val tokens = lexer.makeTokens()
//    println(tokens)

    val yamlContent = File("/Users/maiacamarero/IdeaProjects/print-script/formatter/src/main/kotlin/format_rules.yaml").readText()
    val formatter = Formatter.fromYaml(yamlContent)

    // Usamos el formatter para formatear el código

    val formattedCode = formatter.format(tokens)
    println("Codigo despues del formatter: \n$formattedCode")

    // Ejecutar el analizador de código estático

    // El PARSER toma una lista de tokens y la convierte en un AST
    val parser = Parser(tokens)
    val ast = parser.generateAST()
//    println(ast)

    // El INTERPRETE toma un AST y lo ejecuta
//    val ast = listOf(
//        DeclarationAssignation(
//            Declaration("a", "string"),
//            BinaryOperation(
//                StringOperator("Hello"),
//                "+",
//                NumberOperator(5.0)
//            )
//        ),
//        Method("println", BinaryOperation(IdentifierOperator("a"), "+", StringOperator(" world")))
//    )

    val sca = StaticCodeAnalyzer()
    val scaIssues = sca.analyze(ast)

    // Imprimir problemas encontrados por el analizador de código estático
    if (scaIssues.isNotEmpty()) {
        println("Problemas encontrados por el linter:")
        scaIssues.forEachIndexed { index, issue ->
            println("${index + 1}. ${issue.message} Línea ${issue.position.x}, columna ${issue.position.y}")
        }
        println()
    } else {
        println("No se encontraron problemas de análisis estático.\n")
    }

    val interpreter = Interpreter()
    val result = interpreter.consume(ast)
    println(result)
}
