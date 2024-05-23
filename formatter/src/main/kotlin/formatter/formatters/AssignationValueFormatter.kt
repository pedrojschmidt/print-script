package formatter.formatters

import ast.ASTNode
import ast.BinaryOperation
import ast.BooleanOperator
import ast.IdentifierOperator
import ast.NumberOperator
import ast.StringOperator
import formatter.FormatRules
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
