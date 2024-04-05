class DeclarationASTBuilder: ASTBuilder<Declaration> {
    override fun canBuild(tokenList: List<Token>): Boolean {
        if (tokenList[0].type != TokenType.LET_KEYWORD) return false
        if (tokenList[1].type != TokenType.IDENTIFIER) return false
        if (tokenList[2].type != TokenType.COLON) return false
        if (tokenList[3].type != TokenType.STRING_TYPE && tokenList[3].type != TokenType.NUMBER_TYPE) return false
        if (tokenList.size > 4 && tokenList[4].type == TokenType.EQ) return false
        return true
    }

    override fun build(tokenList: List<Token>): Declaration {
        return Declaration(tokenList[1].value, tokenList[3].value)
    }
}
