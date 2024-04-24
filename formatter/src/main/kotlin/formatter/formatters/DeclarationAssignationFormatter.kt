package formatter.formatters

import ASTNode
import DeclarationAssignation
import formatter.FormatRules
import formatter.Formatter
import kotlin.reflect.KClass

class DeclarationAssignationFormatter : Formatter {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, Formatter>,
    ): String {
        val declarationAssignation = astNode as DeclarationAssignation
        return buildString {
            append(
                if (declarationAssignation.isConst) {
                    "const "
                } else {
                    "let "
                },
            )
            append(DeclarationFormatter().formatNode(declarationAssignation.declaration, rules, formatterList).drop(4).dropLast(2))
            append(
                if (rules.spaceAroundAssignment) {
                    " = "
                } else {
                    "="
                },
            )
            append(AssignationValueFormatter().formatNode(declarationAssignation.value, rules, formatterList))
            append(";\n")
        }
    }
}
