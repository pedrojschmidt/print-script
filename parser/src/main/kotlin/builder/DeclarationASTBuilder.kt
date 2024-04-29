package builder

import Declaration
import Token

class DeclarationASTBuilder(private val version: String) : ASTBuilder<Declaration> {
    override fun verify(statement: List<Token>): Boolean {
        val filteredStatement = filterTokens(statement, listOf(TokenType.NEW_LINE))
        if (filteredStatement.isEmpty()) {
            return false
        } else if (version == "1.0" && filteredStatement[0].type != TokenType.LET_KEYWORD) {
            return false
        } else if (version == "1.1" && filteredStatement[0].type != TokenType.LET_KEYWORD && filteredStatement[0].type != TokenType.CONST_KEYWORD) {
            return false
        } else if (filteredStatement[1].type != TokenType.IDENTIFIER) {
            return false
        } else if (filteredStatement[2].type != TokenType.COLON) {
            return false
        } else if (filteredStatement[3].type != TokenType.STRING_TYPE && filteredStatement[3].type != TokenType.NUMBER_TYPE) {
            return false
        } else if (filteredStatement.size > 4 && filteredStatement[4].type == TokenType.EQ) {
            // Check if the declaration is a simple declaration by checking the 5 token is not a = (if it is, it is an assignation, not a declaration
            return false
        }
        return true
    }

    override fun build(statement: List<Token>): Declaration {
        val filteredStatement = filterTokens(statement, listOf(TokenType.NEW_LINE))
        return Declaration(filteredStatement[1].value, filteredStatement[3].value)
    }
}
