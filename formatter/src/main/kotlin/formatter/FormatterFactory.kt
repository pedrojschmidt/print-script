package formatter

import ASTNode
import Declaration
import formatter.nodeFormatters.DeclarationFormatter
import kotlin.reflect.KClass

class FormatterFactory {
    fun assignFormatters(): Map<KClass<out ASTNode>, FormatterAux> {
        return mapOf(
            Declaration::class to DeclarationFormatter(),
//            DeclarationAssignation::class to DeclarationAssignationFormatter(),
//            SimpleAssignation::class to SimpleAssignationFormatter(),
//            Method::class to MethodFormatter(),
//            StringOperator::class to StringOperatorFormatter(),
//            NumberOperator::class to NumberOperatorFormatter(),
//            BinaryOperation::class to BinaryOperationFormatter()
        )
    }
}
