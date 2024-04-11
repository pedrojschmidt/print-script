package builder

import Declaration
import Token

class DeclarationASTBuilder : ASTBuilder<Declaration> {
    override fun verify(statement: List<Token>): Boolean {
        if (statement[0].type != TokenType.LET_KEYWORD) {
            return false
        } else if (statement[1].type != TokenType.IDENTIFIER) {
            return false
        } else if (statement[2].type != TokenType.COLON) {
            return false
        } else if (statement[3].type != TokenType.STRING_TYPE && statement[3].type != TokenType.NUMBER_TYPE) {
            return false
        } // Check if the declaration is a simple declaration by checking the 5 token is not a = (if it is, it is an assignation, not a declaration
        else if (statement.size > 4 && statement[4].type == TokenType.EQ) {
            return false
        }
        return true
    }

    override fun build(statement: List<Token>): Declaration {
        return Declaration(statement[1].value, statement[3].value)
    }
}
