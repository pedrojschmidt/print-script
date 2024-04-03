import java.io.File

fun main() {
    // git hooks commit test
    val example =
        "let a: number = \"hello\";" +
            "println(a);"
    println("Codigo antes del formatter: \n$example\n")

    val yamlContent = File("/Users/maiacamarero/IdeaProjects/print-script/formatter/src/main/kotlin/format_rules.yaml").readText()
    val formatter = Formatter.fromYaml(yamlContent)

    // Usamos el formatter para formatear el código

    val formattedCode = formatter.format(example)
    println("Codigo despues del formatter: \n$formattedCode")

    // Ejecutar el analizador de código estático en el código formateado

    val sca = StaticCodeAnalyzer()
    val scaIssues = sca.analyze(formattedCode)

    // Imprimir problemas encontrados por el analizador de código estático
    if (scaIssues.isNotEmpty()) {
        println("Problemas encontrados por el analizador de código estático:")
        scaIssues.forEachIndexed { index, issue ->
            println("${index + 1}. ${issue.message} en la línea ${issue.position.x}, columna ${issue.position.y}")
        }
        println()
    } else {
        println("No se encontraron problemas de análisis estático.\n")
    }


    // El LEXER toma un string y lo convierte en una lista de tokens
    val lexer = Lexer(formattedCode)
    val tokens = lexer.makeTokens()
//    println(tokens)

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
    val interpreter = Interpreter()
    val result = interpreter.consume(ast)
    println(result)
}
