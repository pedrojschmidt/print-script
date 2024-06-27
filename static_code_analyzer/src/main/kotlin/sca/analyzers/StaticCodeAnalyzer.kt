package sca.analyzers

import ast.ASTNode
import sca.StaticCodeAnalyzerRules
import sca.StaticCodeIssue

interface StaticCodeAnalyzer {
    fun analyzeNode(
        astNode: ASTNode,
        rules: StaticCodeAnalyzerRules,
    ): List<StaticCodeIssue>
}
