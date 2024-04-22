package formatter.formatters

import ASTNode
import BinaryNode
import BinaryOperation
import IdentifierOperator
import NumberOperator
import StringOperator
import formatter.FormatRules
import formatter.FormatterAux
import kotlin.reflect.KClass

class BinaryOperationFormatter : FormatterAux {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, FormatterAux>,
    ): String {
        val binaryNode = astNode as BinaryNode
        return buildString {
            append(
                when (binaryNode) {
                    is StringOperator -> "\"${binaryNode.value}\""
                    is NumberOperator -> binaryNode.value.toString()
                    is IdentifierOperator -> binaryNode.identifier
                    is BinaryOperation -> "${formatNode(binaryNode.left, rules, formatterList)} ${binaryNode.symbol} ${formatNode(binaryNode.right, rules, formatterList)}"
                    else -> ""
                },
            )
        }
    }
}
