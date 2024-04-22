package formatter

import ASTNode
import BinaryOperation
import Declaration
import DeclarationAssignation
import Method
import SimpleAssignation
import formatter.formatters.BinaryOperationFormatter
import formatter.formatters.DeclarationAssignationFormatter
import formatter.formatters.DeclarationFormatter
import formatter.formatters.MethodFormatter
import formatter.formatters.SimpleAssignationFormatter
import kotlin.reflect.KClass

class FormatterFactory {
    fun assignFormatters(): Map<KClass<out ASTNode>, FormatterAux> {
        return mapOf(
            Declaration::class to DeclarationFormatter(),
            DeclarationAssignation::class to DeclarationAssignationFormatter(),
            SimpleAssignation::class to SimpleAssignationFormatter(),
            Method::class to MethodFormatter(),
            BinaryOperation::class to BinaryOperationFormatter(),
        )
    }
}
