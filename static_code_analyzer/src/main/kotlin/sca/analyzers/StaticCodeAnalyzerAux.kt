package sca.analyzers

import ASTNode
import BinaryNode
import BinaryOperation
import IdentifierOperator
import NumberOperator
import StringOperator
import sca.StaticCodeAnalyzerRules
import sca.StaticCodeIssue
import kotlin.reflect.KClass

interface StaticCodeAnalyzerAux {
    fun analyzeNode(
        astNode: ASTNode,
        rules: StaticCodeAnalyzerRules,
        scaList: Map<KClass<out ASTNode>, StaticCodeAnalyzerAux>,
    ): List<StaticCodeIssue>

    fun checkIdentifierFormat(
        identifier: String,
        rule: Boolean,
    ): Boolean {
        if (!rule) return true
        return identifier.matches("""^[a-z]+(?:[A-Z][a-z\d]*)*$""".toRegex())
    }

    fun extractArgument(value: BinaryNode): String {
        return when (value) {
            is IdentifierOperator -> value.identifier
            is StringOperator -> value.value
            is NumberOperator -> value.value.toString()
            is BinaryOperation -> {
                val left = extractArgument(value.left)
                val right = extractArgument(value.right)
                "$left ${value.symbol} $right"
            }
            else -> ""
        }
    }

    fun checkMethodArgument(
        argument: String,
        rule: Boolean,
    ): Boolean {
        // Verificar si el argumento es un identificador o un literal (número o string)
        if (!rule) return true // Si las reglas están desactivadas, siempre retorna true
        return argument.matches("""^[\w\d]+$""".toRegex())
    }
}
