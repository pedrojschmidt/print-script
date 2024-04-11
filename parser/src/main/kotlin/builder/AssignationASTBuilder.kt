package builder

import Assignation
import DeclarationAssignation
import SimpleAssignation
import Token
import TokenType

class AssignationASTBuilder : ASTBuilder<Assignation> {
    private val declarationASTBuilder = DeclarationASTBuilder()
    private val contentASTBuilder = ContentASTBuilder()

    override fun verify(statement: List<Token>): Boolean {
        return if (isDeclarationAssignation(statement)) {
            contentASTBuilder.verify(statement.subList(5, statement.size))
        } else if (isSimpleAssignation(statement)) {
            contentASTBuilder.verify(statement.subList(2, statement.size))
        } else {
            false
        }
    }

    override fun build(statement: List<Token>): Assignation {
        return if (declarationASTBuilder.verify(statement.subList(0, 4))) {
            DeclarationAssignation(declarationASTBuilder.build(statement.subList(0, 4)), contentASTBuilder.build(statement.subList(5, statement.size - 1)))
        } else {
            SimpleAssignation(statement[0].value, contentASTBuilder.build(statement.subList(2, statement.size)))
        }
    }

    private fun isDeclarationAssignation(tokenList: List<Token>): Boolean {
        return tokenList.size > 4 && declarationASTBuilder.verify(tokenList.subList(0, 4)) && tokenList[4].type == TokenType.EQ
    }

    private fun isSimpleAssignation(tokenList: List<Token>): Boolean {
        return tokenList.size > 2 && tokenList[0].type == TokenType.IDENTIFIER && tokenList[1].type == TokenType.EQ
    }
}
