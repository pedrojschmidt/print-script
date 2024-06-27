package formatter.formatters

import ast.ASTNode
import ast.Conditional
import formatter.FormatRules

class ConditionalFormatter : Formatter {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
    ): String {
        val conditional = astNode as Conditional
        val indentSize = rules.nSpacesIndentationForIfStatement
        val tabs = "\t".repeat(indentSize)

        val formattedThen = conditional.then.joinToString("") { MethodFormatter().formatNode(it, rules).replaceIndent(tabs) }
        val formattedOtherwise = conditional.otherwise?.joinToString("") { MethodFormatter().formatNode(it, rules).replaceIndent(tabs) } ?: ""

        return buildString {
            append("if (")
            append(BinaryOperationFormatter().formatNode(conditional.condition, rules))
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
