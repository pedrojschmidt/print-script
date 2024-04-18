package builder

import ASTNode
import Token
import TokenType

interface ASTBuilder<out T : ASTNode> {
    // Method to verify if this builder can build the AST for the given tokens
    fun verify(statement: List<Token>): Boolean

    // Method to actually build the AST for the given tokens
    fun build(statement: List<Token>): T

    // Filter tokens from certain token type
    fun filterTokens(
        tokens: List<Token>,
        types: List<TokenType>,
    ): List<Token> {
        return tokens.filter { it.type !in types }
    }
}
