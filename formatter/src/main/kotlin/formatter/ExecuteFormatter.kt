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

class ExecuteFormatter(val version: String) : Formatter {
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
            is Conditional -> { // Creo que es inneceario porque si se usó el Parser 1.0 nunca llegaría a haber un nodo Conditional acá
                if (version == "1.1") {
                    ConditionalFormatter().formatNode(astNode, rules, formatterList)
                } else {
                    ""
                }
            }
            is BinaryOperation -> BinaryOperationFormatter().formatNode(astNode, rules, formatterList)
            is Assignation -> AssignationValueFormatter().formatNode(astNode, rules, formatterList)
            else -> ""
        }
    }

    companion object {
        fun getDefaultFormatter(): ExecuteFormatter {
            return getFormatterByVersion("1.1")
        }

        fun getFormatterByVersion(version: String): ExecuteFormatter {
            return ExecuteFormatter(version)
        }
    }
}
