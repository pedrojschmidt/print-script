interface ASTBuilder<out T : ASTNode> {
    // Method to check if this builder can build the AST for the given tokens
    fun canBuild(tokenList: List<Token>): Boolean
    // Method to actually build the AST for the given tokens
    fun build(tokenList: List<Token>): T

    companion object {
        fun removeSemicolon(tokenList: List<Token>): List<Token> {
            return if (tokenList.isNotEmpty() && tokenList.last().type == TokenType.SEMICOLON) {
                tokenList.subList(0, tokenList.size - 1)
            } else {
                tokenList
            }
        }
    }
}
