package builder

import Assignation
import DeclarationAssignation
import SimpleAssignation
import Token
import TokenType

class AssignationASTBuilder : ASTBuilder<Assignation> {
    private val declarationASTBuilder = DeclarationASTBuilder()
    private val contentASTBuilder = ContentASTBuilder()

    override fun verify(tokens: List<Token>): Boolean {
        return if (isDeclarationAssignation(tokens)) {
            contentASTBuilder.verify(tokens.subList(5, tokens.size))
        } else if (isSimpleAssignation(tokens)) {
            contentASTBuilder.verify(tokens.subList(2, tokens.size))
        } else {
            false
        }
    }

    override fun build(tokens: List<Token>): Assignation {
        return if (declarationASTBuilder.verify(tokens.subList(0, 4))) {
            DeclarationAssignation(declarationASTBuilder.build(tokens.subList(0, 4)), contentASTBuilder.build(tokens.subList(5, tokens.size - 1)))
        } else {
            SimpleAssignation(tokens[0].value, contentASTBuilder.build(tokens.subList(2, tokens.size)))
        }
    }

    private fun isDeclarationAssignation(tokenList: List<Token>): Boolean {
        return tokenList.size > 4 && declarationASTBuilder.verify(tokenList.subList(0, 4)) && tokenList[4].type == TokenType.EQ
    }

    private fun isSimpleAssignation(tokenList: List<Token>): Boolean {
        return tokenList.size > 2 && tokenList[0].type == TokenType.IDENTIFIER && tokenList[1].type == TokenType.EQ
    }
}
