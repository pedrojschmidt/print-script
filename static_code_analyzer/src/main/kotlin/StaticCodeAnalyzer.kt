import org.yaml.snakeyaml.Yaml

class StaticCodeAnalyzer(private val scaRules: StaticCodeAnalyzerRules) {

    companion object {
        fun fromYaml(yamlContent: String): StaticCodeAnalyzer {
            val yaml = Yaml()
            val yamlMap = yaml.load(yamlContent) as Map<String, Map<String, Any>>
            val rulesMap = yamlMap["rules"] ?: throw IllegalArgumentException("Invalid YAML content")
            val printlnArgumentCheck = rulesMap["printlnArgumentCheck"] as Boolean

            val formatRules =
                StaticCodeAnalyzerRules(
                    printlnArgumentCheck,
                )
            return StaticCodeAnalyzer(formatRules)
        }
    }

    fun analyze(astNodes: List<ASTNode>): List<StaticCodeIssue> {
        val issues = mutableListOf<StaticCodeIssue>()
        var lineIndex = 1
        var columnIndex = 1

        for (node in astNodes) {
            when (node) {
                is DeclarationAssignation -> {
                    if (!checkTypeMatching(node)) {
                        issues.add(StaticCodeIssue("La declaración de variable no coincide con el tipo del valor asignado", Position(lineIndex, columnIndex)))
                    }
                    if (!checkIdentifierFormat(node.declaration.identifier)) {
                        issues.add(StaticCodeIssue("El identificador '${node.declaration.identifier}' debe estar en lower camel case.", Position(lineIndex, columnIndex)))
                    }
                }
                is Method -> {
                    if (node.identifier == "println") {
                        val argument = extractPrintlnArgument(node.value)
                        if (!isValidPrintlnArgument(argument)) {
                            issues.add(StaticCodeIssue("La función println debe llamarse solo con un identificador o un literal, la expresión: $argument es inválida.", Position(lineIndex, columnIndex)))
                        }
                    }
                }

                else -> {}
            }

            if (node is Method) {
                lineIndex++ // Aumentar el índice de línea si el nodo es un método
            }

            columnIndex++
        }

        return issues
    }

    private fun checkTypeMatching(node: DeclarationAssignation): Boolean {
        return when (node.declaration.type) {
            "string" -> node.assignation is StringOperator
            "number" -> node.assignation is NumberOperator || node.assignation is BinaryOperation
            else -> false
        }
    }

    private fun extractPrintlnArgument(value: BinaryNode): String {
        return when (value) {
            is IdentifierOperator -> value.identifier
            is StringOperator -> value.value
            is NumberOperator -> value.value.toString()
            is BinaryOperation -> {
                val left = extractPrintlnArgument(value.left)
                val right = extractPrintlnArgument(value.right)
                "$left ${value.symbol} $right"
            }
            else -> ""
        }
    }

    private fun isValidPrintlnArgument(argument: String): Boolean {
        // Verificar si el argumento es un identificador o un literal
        return argument.matches("""^[\w\d]+$""".toRegex()) // Asumiendo que un identificador puede contener letras y números
    }

    private fun checkIdentifierFormat(identifier: String): Boolean {
        // Verificar si el identificador está en lower camel case
        return identifier.matches("""^[a-z]+(?:[A-Z][a-z\d]*)*$""".toRegex())
    }
}
