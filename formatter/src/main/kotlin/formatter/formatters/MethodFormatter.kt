package formatter.formatters

import ast.ASTNode
import ast.BinaryOperation
import ast.IdentifierOperator
import ast.Method
import ast.NumberOperator
import ast.StringOperator
import formatter.FormatRules
import kotlin.reflect.KClass

class MethodFormatter : Formatter {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, Formatter>,
    ): String {
        val method = astNode as Method
        val newlineBefore = "\n".repeat(rules.newlineBeforePrintln)
        return buildString {
            append(newlineBefore)
            append(method.identifier)
            append("(")
            append(
                when (val methodValue = method.value) {
                    is StringOperator -> "\"${methodValue.value}\""
                    is NumberOperator -> methodValue.value.toString()
                    is BinaryOperation -> BinaryOperationFormatter().formatNode(methodValue, rules, formatterList)
                    is IdentifierOperator -> methodValue.identifier
                    else -> ""
                },
            )
            append(");\n")
        }
    }
}
