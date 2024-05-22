package sca.analyzers

import ASTNode
import sca.StaticCodeAnalyzerRules
import sca.StaticCodeIssue
import kotlin.reflect.KClass

interface StaticCodeAnalyzer {
    fun analyzeNode(
        astNode: ASTNode,
        rules: StaticCodeAnalyzerRules,
        scaList: Map<KClass<out ASTNode>, StaticCodeAnalyzer>,
    ): List<StaticCodeIssue>
}
