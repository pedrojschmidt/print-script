class ContentASTBuilder : ASTBuilder<BinaryNode> {
    override fun canBuild(tokenList: List<Token>): Boolean {
        return tokenList.isNotEmpty()
    }

    override fun build(tokenList: List<Token>): BinaryNode {
        if (tokenList.size == 1) {
            val token = tokenList[0]
            return when (token.type) {
                TokenType.NUMBER -> NumberOperator(token.value.toDouble())
                TokenType.STRING -> StringOperator(token.value)
                TokenType.IDENTIFIER -> IdentifierOperator(token.value)
                else -> throw RuntimeException("Token de tipo ${token.type} inesperado en la línea ${token.positionStart.x}:${token.positionStart.y}",)
            }
        }
        else {
            val leftOperand = tokenList[0]
            val operatorToken = tokenList[1]
            return when (leftOperand.type) {
                TokenType.STRING -> {
                    BinaryOperation(StringOperator(leftOperand.value), operatorToken.value, build(tokenList.subList(2, tokenList.size)))
                }
                TokenType.NUMBER -> {
                    BinaryOperation(NumberOperator(leftOperand.value.toDouble()), operatorToken.value, build(tokenList.subList(2, tokenList.size)))
                }
                TokenType.IDENTIFIER -> {
                    BinaryOperation(IdentifierOperator(leftOperand.value), operatorToken.value, build(tokenList.subList(2, tokenList.size)))
                }
                else -> throw RuntimeException("Token de tipo ${leftOperand.type} inesperado en la línea ${leftOperand.positionStart.x}:${leftOperand.positionStart.y}",)
            }
        }
    }
}
