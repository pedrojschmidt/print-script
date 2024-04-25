package formatter

import ASTNode
import kotlin.reflect.KClass

interface Formatter {
    fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, Formatter>,
    ): String
}
