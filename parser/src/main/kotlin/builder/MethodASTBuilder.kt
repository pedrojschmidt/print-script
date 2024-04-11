package builder

import Method
import Token
import TokenType

class MethodASTBuilder : ASTBuilder<Method> {
    private val contentASTBuilder = ContentASTBuilder()

    override fun verify(tokens: List<Token>): Boolean {
        return if (tokens.size > 3 && tokens[0].type == TokenType.PRINTLN_FUNCTION && tokens[1].type == TokenType.LPAREN && tokens[tokens.size - 1].type == TokenType.RPAREN) {
            contentASTBuilder.verify(tokens.subList(2, tokens.size - 1))
        } else {
            false
        }
    }

    override fun build(tokens: List<Token>): Method {
        return Method(tokens[0].value, contentASTBuilder.build(tokens.subList(2, tokens.size - 1)))
    }
}
