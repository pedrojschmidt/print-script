package formatter.formatters

import ASTNode
import BinaryOperation
import IdentifierOperator
import NumberOperator
import StringOperator
import ValueNode
import formatter.FormatRules
import kotlin.reflect.KClass

class BinaryOperationFormatter : Formatter {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, Formatter>,
    ): String {
        val valueNode = astNode as ValueNode
        return buildString {
            append(
                when (valueNode) {
                    is StringOperator -> "\"${valueNode.value}\""
                    is NumberOperator -> valueNode.value
                    is IdentifierOperator -> valueNode.identifier
                    is BinaryOperation -> "${formatNode(valueNode.left, rules, formatterList)} ${valueNode.symbol} ${formatNode(valueNode.right, rules, formatterList)}"
                    else -> ""
                },
            )
        }
    }
}
