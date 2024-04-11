package builder

import Declaration
import Token

class DeclarationASTBuilder : ASTBuilder<Declaration> {
    override fun verify(tokens: List<Token>): Boolean {
        if (tokens[0].type != TokenType.LET_KEYWORD) return false
        else if (tokens[1].type != TokenType.IDENTIFIER) return false
        else if (tokens[2].type != TokenType.COLON) return false
        else if (tokens[3].type != TokenType.STRING_TYPE && tokens[3].type != TokenType.NUMBER_TYPE) return false
        else if (tokens.size > 4 && tokens[4].type == TokenType.EQ) return false
        return true
    }

    override fun build(tokens: List<Token>): Declaration {
        return Declaration(tokens[1].value, tokens[3].value)
    }
}
