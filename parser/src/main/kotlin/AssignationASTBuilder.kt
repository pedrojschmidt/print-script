class AssignationASTBuilder : ASTBuilder<Assignation> {
    private val declarationASTBuilder = DeclarationASTBuilder()
    private val contentASTBuilder = ContentASTBuilder()
    override fun canBuild(tokenList: List<Token>): Boolean {
        return if (isDeclarationAssignation(tokenList)) {
            contentASTBuilder.canBuild(tokenList.subList(5, tokenList.size))
        } else if (isSimpleAssignation(tokenList)) {
            contentASTBuilder.canBuild(tokenList.subList(2, tokenList.size))
        } else {
            false
        }
    }

    override fun build(tokenList: List<Token>): Assignation {
        return if (declarationASTBuilder.canBuild(tokenList.subList(0, 4))) {
            DeclarationAssignation(declarationASTBuilder.build(tokenList.subList(0, 4)), contentASTBuilder.build(tokenList.subList(5, tokenList.size - 1)))
        } else {
            SimpleAssignation(tokenList[0].value, contentASTBuilder.build(tokenList.subList(2, tokenList.size)))
        }
    }

    private fun isDeclarationAssignation(tokenList: List<Token>): Boolean {
        return tokenList.size > 4 && declarationASTBuilder.canBuild(tokenList.subList(0, 4)) && tokenList[4].type == TokenType.EQ
    }

    private fun isSimpleAssignation(tokenList: List<Token>): Boolean {
        return tokenList.size > 2 && tokenList[0].type == TokenType.IDENTIFIER && tokenList[1].type == TokenType.EQ
    }
}
