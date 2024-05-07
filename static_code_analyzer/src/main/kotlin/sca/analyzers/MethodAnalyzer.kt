package sca.analyzers

import ASTNode
import Method
import Position
import sca.StaticCodeAnalyzerRules
import sca.StaticCodeIssue
import kotlin.reflect.KClass

class MethodAnalyzer : StaticCodeAnalyzerAux {
    override fun analyzeNode(
        astNode: ASTNode,
        rules: StaticCodeAnalyzerRules,
        scaList: Map<KClass<out ASTNode>, StaticCodeAnalyzerAux>,
    ): List<StaticCodeIssue> {
        val methodNode = astNode as Method
        val issues = mutableListOf<StaticCodeIssue>()
        val lineIndex = 1
        var columnIndex = 1

        if (methodNode.identifier == "println") {
            val argument = extractArgument(methodNode.value)
            if (!checkMethodArgument(argument, rules.functionArgumentCheck)) {
                columnIndex++
                issues.add(StaticCodeIssue("The println function should be called only with an identifier or a literal, the expression: $argument is invalid.", Position(lineIndex, columnIndex)))
            }
        }
        return issues
    }
}
