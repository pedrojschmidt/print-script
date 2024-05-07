package sca.analyzers

import ASTNode
import BinaryOperation
import DeclarationAssignation
import NumberOperator
import Position
import StringOperator
import sca.StaticCodeAnalyzerRules
import sca.StaticCodeIssue
import kotlin.reflect.KClass

class DeclarationAssignationAnalyzer : StaticCodeAnalyzerAux {
    override fun analyzeNode(
        astNode: ASTNode,
        rules: StaticCodeAnalyzerRules,
        scaList: Map<KClass<out ASTNode>, StaticCodeAnalyzerAux>,
    ): List<StaticCodeIssue> {
        val declarationAssignation = astNode as DeclarationAssignation
        val issues = mutableListOf<StaticCodeIssue>()
        val lineIndex = 1
        var columnIndex = 1
        if (!checkTypeMatching(declarationAssignation, rules.typeMatchingCheck)) {
            columnIndex += 3
            issues.add(StaticCodeIssue("Variable declaration does not match the type of the assigned value", Position(lineIndex, columnIndex)))
        }
        if (!checkIdentifierFormat(declarationAssignation.declaration.identifier, rules.identifierNamingCheck)) {
            columnIndex += 1
            issues.add(StaticCodeIssue("The identifier '${declarationAssignation.declaration.identifier}' must be in lower camel case.", Position(lineIndex, columnIndex)))
        }
        return issues
    }

    private fun checkTypeMatching(
        node: ASTNode,
        rule: Boolean,
    ): Boolean {
        if (!rule) return true
        return when (node) {
            is DeclarationAssignation -> {
                when (node.declaration.type) {
                    "string" -> node.value is StringOperator
                    "number" -> node.value is NumberOperator || node.value is BinaryOperation
                    else -> false
                }
            }

            else -> false
        }
    }
}
