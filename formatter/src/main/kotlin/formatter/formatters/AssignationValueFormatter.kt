package formatter.formatters

import ASTNode
import BinaryOperation
import IdentifierOperator
import NumberOperator
import StringOperator
import formatter.FormatRules
import formatter.FormatterAux
import kotlin.reflect.KClass

class AssignationValueFormatter : FormatterAux {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, FormatterAux>,
    ): String {
        return when (astNode) {
            is StringOperator -> "\"${astNode.value}\""
            is NumberOperator -> "${astNode.value}"
            is IdentifierOperator -> astNode.identifier
            is BinaryOperation -> BinaryOperationFormatter().formatNode(astNode, rules, formatterList)
            else -> ""
        }
    }
}
