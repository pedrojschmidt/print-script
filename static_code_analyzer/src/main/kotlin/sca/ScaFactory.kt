package sca

import ASTNode
import DeclarationAssignation
import Method
import sca.analyzers.DeclarationAssignationAnalyzer
import sca.analyzers.MethodAnalyzer
import sca.analyzers.StaticCodeAnalyzerAux
import kotlin.reflect.KClass

class ScaFactory {
    fun assignAnalyzers(): Map<KClass<out ASTNode>, StaticCodeAnalyzerAux> {
        return mapOf(
            DeclarationAssignation::class to DeclarationAssignationAnalyzer(),
            Method::class to MethodAnalyzer(),
        )
    }
}
