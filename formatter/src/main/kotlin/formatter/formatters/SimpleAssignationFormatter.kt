package formatter.formatters

import ASTNode
import SimpleAssignation
import formatter.FormatRules
import formatter.Formatter
import kotlin.reflect.KClass

class SimpleAssignationFormatter : Formatter {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, Formatter>,
    ): String {
        val simpleAssignation = astNode as SimpleAssignation
        return buildString {
            append(simpleAssignation.identifier)
            append(
                if (rules.spaceAroundAssignment) {
                    " = " // Space before and after assignation
                } else {
                    "=" // No space around assignation
                },
            )
            append(AssignationValueFormatter().formatNode(simpleAssignation.value, rules, formatterList))
            append(";\n")
        }
    }
}
