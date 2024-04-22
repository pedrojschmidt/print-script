package formatter.nodeFormatters

import ASTNode
import Declaration
import formatter.FormatRules
import formatter.FormatterAux
import kotlin.reflect.KClass

class DeclarationFormatter : FormatterAux {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, FormatterAux>,
    ): String {
        val declaration = astNode as Declaration
        return buildString {
            append("let ")
            append(declaration.identifier)
            append(
                if (rules.spaceBeforeColon && rules.spaceAfterColon) {
                    " : " // Space before and after colon
                } else if (rules.spaceAfterColon) {
                    ": " // Space after colon
                } else if (rules.spaceBeforeColon) {
                    " :" // Space before colon
                } else {
                    ":"
                },
            )
            append(declaration.type)
            append(";")
        }
    }
// return "let${applyLetFormatting()}${astNode.identifier}${applyColonFormatting()}${astNode.type};${applySemicolonFormatting()}"
}
