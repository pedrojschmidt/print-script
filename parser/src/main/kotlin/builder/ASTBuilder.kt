package builder

import ASTNode
import Token

interface ASTBuilder<out T : ASTNode> {
    // Method to verify if this builder can build the AST for the given tokens
    fun verify(tokens: List<Token>): Boolean

    // Method to actually build the AST for the given tokens
    fun build(tokens: List<Token>): T
}
