package sca

import ASTNode
import sca.analyzers.StaticCodeAnalyzerAux
import kotlin.reflect.KClass

class ExecuteSca : StaticCodeAnalyzerAux {
    override fun analyzeNode(
        astNode: ASTNode,
        rules: StaticCodeAnalyzerRules,
        scaList: Map<KClass<out ASTNode>, StaticCodeAnalyzerAux>,
    ): List<StaticCodeIssue> {
        val issues = mutableListOf<StaticCodeIssue>()
        issues.addAll(scaList[astNode::class]?.analyzeNode(astNode, rules, scaList) ?: emptyList())
        return issues
    }
}
