package builder

import Method
import Token
import TokenType

class MethodASTBuilder : ASTBuilder<Method> {
    private val contentASTBuilder = ContentASTBuilder()

    override fun verify(statement: List<Token>): Boolean {
        return if (statement.size > 3 && statement[0].type == TokenType.PRINTLN_FUNCTION && statement[1].type == TokenType.LPAREN && statement[statement.size - 2].type == TokenType.RPAREN) {
            contentASTBuilder.verify(statement.subList(2, statement.size - 1))
        } else {
            false
        }
    }

    override fun build(statement: List<Token>): Method {
        return Method(statement[0].value, contentASTBuilder.build(statement.subList(2, statement.size - 2)))
    }
}
