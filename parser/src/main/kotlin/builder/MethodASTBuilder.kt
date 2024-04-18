package builder

import Method
import Token
import TokenType

class MethodASTBuilder : ASTBuilder<Method> {
    private val contentASTBuilder = ContentASTBuilder()

    override fun verify(statement: List<Token>): Boolean {
        val filteredStatement = filterTokens(statement, listOf(TokenType.NEW_LINE))
        return if (filteredStatement.size > 3 && filteredStatement[0].type == TokenType.PRINTLN_FUNCTION && filteredStatement[1].type == TokenType.LPAREN && filteredStatement[filteredStatement.size - 2].type == TokenType.RPAREN) {
            contentASTBuilder.verify(filteredStatement.subList(2, filteredStatement.size - 1))
        } else {
            false
        }
    }

    override fun build(statement: List<Token>): Method {
        val filteredStatement = filterTokens(statement, listOf(TokenType.NEW_LINE))
        return Method(filteredStatement[0].value, contentASTBuilder.build(filteredStatement.subList(2, filteredStatement.size - 2)))
    }
}
