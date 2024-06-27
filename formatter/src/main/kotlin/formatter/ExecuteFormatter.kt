package formatter

import ast.ASTNode
import ast.BinaryOperation
import ast.Conditional
import ast.Declaration
import ast.DeclarationAssignation
import ast.Method
import ast.SimpleAssignation
import formatter.formatters.BinaryOperationFormatter
import formatter.formatters.ConditionalFormatter
import formatter.formatters.DeclarationAssignationFormatter
import formatter.formatters.DeclarationFormatter
import formatter.formatters.Formatter
import formatter.formatters.MethodFormatter
import formatter.formatters.SimpleAssignationFormatter
import kotlin.reflect.KClass

class ExecuteFormatter(val version: String, val formatterList: Map<KClass<out ASTNode>, Formatter>) : Formatter {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
    ): String {
//        return when (astNode) {
//            is Declaration -> DeclarationFormatter().formatNode(astNode, rules, formatterList)
//            is DeclarationAssignation -> DeclarationAssignationFormatter().formatNode(astNode, rules, formatterList)
//            is SimpleAssignation -> SimpleAssignationFormatter().formatNode(astNode, rules, formatterList)
//            is Method -> MethodFormatter().formatNode(astNode, rules, formatterList)
//            is Conditional -> { // Creo que es inneceario porque si se usó el Parser 1.0 nunca llegaría a haber un nodo Conditional acá
//                if (version == "1.1") {
//                    ConditionalFormatter().formatNode(astNode, rules, formatterList)
//                } else {
//                    ""
//                }
//            }
//            is BinaryOperation -> BinaryOperationFormatter().formatNode(astNode, rules, formatterList)
//            is Assignation -> AssignationValueFormatter().formatNode(astNode, rules, formatterList)
//            else -> ""
//        }
        val formatter = formatterList[astNode::class]
        return if (astNode is Conditional && version != "1.1") {
            ""
        } else {
            formatter?.formatNode(astNode, rules) ?: ""
        }
    }

    companion object {
        fun getDefaultFormatter(): ExecuteFormatter {
            return getFormatterByVersion("1.1")
        }

        fun getFormatterByVersion(version: String): ExecuteFormatter {
            val formatters: Map<KClass<out ASTNode>, Formatter> =
                when (version) {
                    "1.0" ->
                        mapOf(
                            Declaration::class to DeclarationFormatter(),
                            DeclarationAssignation::class to DeclarationAssignationFormatter(),
                            SimpleAssignation::class to SimpleAssignationFormatter(),
                            Method::class to MethodFormatter(),
                            BinaryOperation::class to BinaryOperationFormatter(),
                        )
                    "1.1" ->
                        mapOf(
                            Declaration::class to DeclarationFormatter(),
                            DeclarationAssignation::class to DeclarationAssignationFormatter(),
                            SimpleAssignation::class to SimpleAssignationFormatter(),
                            Method::class to MethodFormatter(),
                            BinaryOperation::class to BinaryOperationFormatter(),
                            Conditional::class to ConditionalFormatter(),
                        )
                    else -> mapOf()
                }
            return ExecuteFormatter(version, formatters)
        }
    }
}
