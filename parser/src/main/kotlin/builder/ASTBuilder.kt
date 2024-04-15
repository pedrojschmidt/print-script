package builder

import ASTNode
import version_0.Token

interface ASTBuilder<out T : ASTNode> {
    // Method to verify if this builder can build the AST for the given tokens
    fun verify(statement: List<Token>): Boolean

    // Method to actually build the AST for the given tokens
    fun build(statement: List<Token>): T
}
