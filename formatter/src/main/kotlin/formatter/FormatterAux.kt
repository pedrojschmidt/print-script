package formatter

import ASTNode
import kotlin.reflect.KClass

interface FormatterAux {
    fun formatNode(
        astNode: ASTNode,
        rules: FormatRules,
        formatterList: Map<KClass<out ASTNode>, FormatterAux>,
    ): String
}
