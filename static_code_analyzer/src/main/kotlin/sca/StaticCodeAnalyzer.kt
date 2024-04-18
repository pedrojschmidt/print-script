package sca

import ast.ASTNode
import ast.BinaryNode
import ast.BinaryOperation
import ast.DeclarationAssignation
import ast.IdentifierOperator
import ast.Method
import ast.NumberOperator
import ast.StringOperator
import org.yaml.snakeyaml.Yaml
import token.Position

class StaticCodeAnalyzer(private val scaRules: StaticCodeAnalyzerRules) {
    companion object {
        fun fromYaml(yamlContent: String): StaticCodeAnalyzer {
            val yaml = Yaml()
            val yamlMap = yaml.load(yamlContent) as Map<String, Map<String, Any>>
            val rulesMap = yamlMap["rules"] ?: throw IllegalArgumentException("Invalid YAML content")
            val printlnArgumentCheck = rulesMap["printlnArgumentCheck"] as Boolean
            val typeMatchingCheck = rulesMap["typeMatchingCheck"] as Boolean
            val identifierNamingCheck = rulesMap["identifierNamingCheck"] as Boolean

            val formatRules =
                StaticCodeAnalyzerRules(
                    printlnArgumentCheck,
                    typeMatchingCheck,
                    identifierNamingCheck,
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
                        columnIndex += 3
                        issues.add(StaticCodeIssue("La declaración de variable no coincide con el tipo del valor asignado", Position(lineIndex, columnIndex)))
                    }
                    if (!checkIdentifierFormat(node.declaration.identifier)) {
                        columnIndex += 1
                        issues.add(StaticCodeIssue("El identificador '${node.declaration.identifier}' debe estar en lower camel case.", Position(lineIndex, columnIndex)))
                    }
                }
                is Method -> {
                    if (node.identifier == "println") {
                        val argument = extractPrintlnArgument(node.value)
                        if (!isValidPrintlnArgument(argument)) {
                            columnIndex++
                            issues.add(StaticCodeIssue("La función println debe llamarse solo con un identificador o un literal, la expresión: $argument es inválida.", Position(lineIndex, columnIndex)))
                        }
                    }
                }

                else -> {}
            }
            lineIndex++
        }

        return issues
    }

    private fun checkTypeMatching(node: DeclarationAssignation): Boolean {
        // Verificar si el tipo de la declaración coincide con el tipo del valor asignado
        if (!scaRules.typeMatchingCheck) return true // Si las reglas están desactivadas, siempre retorna true

        return when (node.declaration.type) {
            "string" -> node.value is StringOperator
            "number" -> node.value is NumberOperator || node.value is BinaryOperation
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
        // Verificar si el argumento es un identificador o un literal (número o string)
        if (!scaRules.printlnArgumentCheck) return true // Si las reglas están desactivadas, siempre retorna true

        return argument.matches("""^[\w\d]+$""".toRegex())
    }

    private fun checkIdentifierFormat(identifier: String): Boolean {
        // Verificar si el identificador está en lower camel case (e.g. nombreVariable)
        if (!scaRules.identifierNamingCheck) return true
        return identifier.matches("""^[a-z]+(?:[A-Z][a-z\d]*)*$""".toRegex())
    }
}
