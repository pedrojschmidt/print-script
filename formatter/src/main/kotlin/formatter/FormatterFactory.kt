package formatter

import ast.ASTNode
import ast.BinaryOperation
import ast.Conditional
import ast.Declaration
import ast.DeclarationAssignation
import ast.Method
import ast.SimpleAssignation
import formatter.formatters.BinaryOperationFormatter
import formatter.formatters.ConditionalFormatter
import formatter.formatters.DeclarationAssignationFormatter
import formatter.formatters.DeclarationFormatter
import formatter.formatters.Formatter
import formatter.formatters.MethodFormatter
import formatter.formatters.SimpleAssignationFormatter
import kotlin.reflect.KClass

class
FormatterFactory {
    fun assignFormatters(): Map<KClass<out ASTNode>, Formatter> {
        return mapOf(
            Declaration::class to DeclarationFormatter(),
            DeclarationAssignation::class to DeclarationAssignationFormatter(),
            SimpleAssignation::class to SimpleAssignationFormatter(),
            Method::class to MethodFormatter(),
            BinaryOperation::class to BinaryOperationFormatter(),
            Conditional::class to ConditionalFormatter(),
        )
    }
}
