package sca

import ASTNode
import DeclarationAssignation
import Method
import sca.analyzers.DeclarationAssignationAnalyzer
import sca.analyzers.MethodAnalyzer
import sca.analyzers.StaticCodeAnalyzer
import kotlin.reflect.KClass

class ExecuteSca : StaticCodeAnalyzer {
    override fun analyzeNode(
        astNode: ASTNode,
        rules: StaticCodeAnalyzerRules,
        scaList: Map<KClass<out ASTNode>, StaticCodeAnalyzer>,
    ): List<StaticCodeIssue> {
        val issues = mutableListOf<StaticCodeIssue>()
        when (astNode) {
            is DeclarationAssignation ->
                DeclarationAssignationAnalyzer().analyzeNode(astNode, rules, scaList).forEach {
                    issues.add(it)
                }
            is Method ->
                MethodAnalyzer().analyzeNode(astNode, rules, scaList).forEach {
                    issues.add(it)
                }
            else -> println("No analyzer for this node")
        }
        return issues
    }

    companion object {
        fun getDefaultSCA(): ExecuteSca {
            return ExecuteSca()
        }

        fun getSCAByVersion(version: String): ExecuteSca {
            return ExecuteSca()
        }
    }
}
