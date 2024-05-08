package formatter.formatters

import ASTNode
import BinaryNode
import BinaryOperation
import IdentifierOperator
import NumberOperator
import StringOperator
import formatter.FormatRules
import kotlin.reflect.KClass

class BinaryOperationFormatter : Formatter {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, Formatter>,
    ): String {
        val binaryNode = astNode as BinaryNode
        return buildString {
            append(
                when (binaryNode) {
                    is StringOperator -> "\"${binaryNode.value}\""
                    is NumberOperator -> binaryNode.value
                    is IdentifierOperator -> binaryNode.identifier
                    is BinaryOperation -> "${formatNode(binaryNode.left, rules, formatterList)} ${binaryNode.symbol} ${formatNode(binaryNode.right, rules, formatterList)}"
                    else -> ""
                },
            )
        }
    }
}
