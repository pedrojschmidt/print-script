package formatter.formatters

import ASTNode
import BinaryOperation
import BooleanOperator
import IdentifierOperator
import NumberOperator
import StringOperator
import formatter.FormatRules
import formatter.Formatter
import kotlin.reflect.KClass

class AssignationValueFormatter : Formatter {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, Formatter>,
    ): String {
        return when (astNode) {
            is StringOperator -> "\"${astNode.value}\""
            is NumberOperator -> "${astNode.value}"
            is IdentifierOperator -> astNode.identifier
            is BooleanOperator -> astNode.value
            is BinaryOperation -> BinaryOperationFormatter().formatNode(astNode, rules, formatterList)
            else -> ""
        }
    }
}
