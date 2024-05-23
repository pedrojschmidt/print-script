package sca

import ast.ASTNode
import ast.DeclarationAssignation
import ast.Method
import sca.analyzers.DeclarationAssignationAnalyzer
import sca.analyzers.MethodAnalyzer
import sca.analyzers.StaticCodeAnalyzer
import kotlin.reflect.KClass

class ScaFactory {
    fun assignAnalyzers(): Map<KClass<out ASTNode>, StaticCodeAnalyzer> {
        return mapOf(
            DeclarationAssignation::class to DeclarationAssignationAnalyzer(),
            Method::class to MethodAnalyzer(),
        )
    }
}
