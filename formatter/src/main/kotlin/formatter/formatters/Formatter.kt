package formatter.formatters

import ASTNode
import formatter.FormatRules
import kotlin.reflect.KClass

interface Formatter {
    fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, Formatter>,
    ): String
}
