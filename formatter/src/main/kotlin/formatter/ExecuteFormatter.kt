package formatter

import ASTNode
import Declaration
import formatter.nodeFormatters.DeclarationFormatter
import kotlin.reflect.KClass

class ExecuteFormatter : FormatterAux {
    override fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, FormatterAux>,
    ): String {
        return when (astNode) {
            is Declaration -> DeclarationFormatter().formatNode(astNode, rules, formatterList)
//            is Println -> PrintlnNodeFormatter().formatNode(astNode, rules, formatterList)
            else -> ""
        }
    }
}
