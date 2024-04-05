class MethodASTBuilder : ASTBuilder<Method> {
    private val contentASTBuilder = ContentASTBuilder()
    override fun canBuild(tokenList: List<Token>): Boolean {
        return if (tokenList.size > 3 && tokenList[0].type == TokenType.PRINTLN_FUNCTION && tokenList[1].type == TokenType.LPAREN && tokenList[tokenList.size - 1].type == TokenType.RPAREN) {
            contentASTBuilder.canBuild(tokenList.subList(2, tokenList.size - 1))
        } else {
            false
        }
    }

    override fun build(tokenList: List<Token>): Method {
        return Method(tokenList[0].value, contentASTBuilder.build(tokenList.subList(2, tokenList.size - 1)))
    }
}
