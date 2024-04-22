package formatter

import ASTNode
import Declaration
import DeclarationAssignation
import Method
import SimpleAssignation
import formatter.formatters.DeclarationAssignationFormatter
import formatter.formatters.DeclarationFormatter
import formatter.formatters.MethodFormatter
import formatter.formatters.SimpleAssignationFormatter
import kotlin.reflect.KClass

class ExecuteFormatter : FormatterAux {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, FormatterAux>,
    ): String {
        return when (astNode) {
            is Declaration -> DeclarationFormatter().formatNode(astNode, rules, formatterList)
            is DeclarationAssignation -> DeclarationAssignationFormatter().formatNode(astNode, rules, formatterList)
            is SimpleAssignation -> SimpleAssignationFormatter().formatNode(astNode, rules, formatterList)
            is Method -> MethodFormatter().formatNode(astNode, rules, formatterList)
            else -> ""
        }
    }
}
