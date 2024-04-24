package formatter.formatters

import ASTNode
import Conditional
import formatter.FormatRules
import formatter.Formatter
import kotlin.reflect.KClass

class ConditionalFormatter : Formatter {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, Formatter>,
    ): String {
        val conditional = astNode as Conditional
        val indentSize = rules.nSpacesIndentationForIfStatement
        val tabs = "\t".repeat(indentSize)

        val formattedThen = conditional.then.joinToString("") { MethodFormatter().formatNode(it, rules, formatterList).replaceIndent(tabs) }
        val formattedOtherwise = conditional.otherwise?.joinToString("") { MethodFormatter().formatNode(it, rules, formatterList).replaceIndent(tabs) } ?: ""

        return buildString {
            append("if (")
            append(BinaryOperationFormatter().formatNode(conditional.condition, rules, formatterList))
            append(") {\n")
            append(formattedThen)
            append("\n}")
            if (conditional.otherwise != null) {
                append(" else {\n")
                append(formattedOtherwise)
                append("\n}")
            }
            append("\n")
        }
    }
}
