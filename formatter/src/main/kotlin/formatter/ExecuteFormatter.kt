package formatter

import ASTNode
import Assignation
import BinaryOperation
import Conditional
import Declaration
import DeclarationAssignation
import Method
import SimpleAssignation
import formatter.formatters.AssignationValueFormatter
import formatter.formatters.BinaryOperationFormatter
import formatter.formatters.ConditionalFormatter
import formatter.formatters.DeclarationAssignationFormatter
import formatter.formatters.DeclarationFormatter
import formatter.formatters.MethodFormatter
import formatter.formatters.SimpleAssignationFormatter
import kotlin.reflect.KClass

class ExecuteFormatter : Formatter {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, Formatter>,
    ): String {
        return when (astNode) {
            is Declaration -> DeclarationFormatter().formatNode(astNode, rules, formatterList)
            is DeclarationAssignation -> DeclarationAssignationFormatter().formatNode(astNode, rules, formatterList)
            is SimpleAssignation -> SimpleAssignationFormatter().formatNode(astNode, rules, formatterList)
            is Method -> MethodFormatter().formatNode(astNode, rules, formatterList)
            is Conditional -> ConditionalFormatter().formatNode(astNode, rules, formatterList)
            is BinaryOperation -> BinaryOperationFormatter().formatNode(astNode, rules, formatterList)
            is Assignation -> AssignationValueFormatter().formatNode(astNode, rules, formatterList)
            else -> ""
        }
    }
}
