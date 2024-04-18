package parser.builder

import ast.Assignation
import ast.DeclarationAssignation
import ast.SimpleAssignation
import token.Token
import token.TokenType

class AssignationASTBuilder : ASTBuilder<Assignation> {
    private val declarationASTBuilder = DeclarationASTBuilder()
    private val valueASTBuilder = ValueASTBuilder()

    override fun verify(statement: List<Token>): Boolean {
        val filteredStatement = filterTokens(statement, listOf(TokenType.NEW_LINE))
        return if (isDeclarationAssignation(filteredStatement)) {
            valueASTBuilder.verify(filteredStatement.subList(5, filteredStatement.size))
        } else if (isSimpleAssignation(filteredStatement)) {
            valueASTBuilder.verify(filteredStatement.subList(2, filteredStatement.size))
        } else {
            false
        }
    }

    override fun build(statement: List<Token>): Assignation {
        val filteredStatement = filterTokens(statement, listOf(TokenType.NEW_LINE))
        return if (declarationASTBuilder.verify(filteredStatement.subList(0, 4))) {
            DeclarationAssignation(declarationASTBuilder.build(filteredStatement.subList(0, 4)), valueASTBuilder.build(filteredStatement.subList(5, filteredStatement.size - 1)))
        } else {
            SimpleAssignation(filteredStatement[0].value, valueASTBuilder.build(filteredStatement.subList(2, filteredStatement.size)))
        }
    }

    private fun isDeclarationAssignation(tokenList: List<Token>): Boolean {
        return tokenList.size > 4 && declarationASTBuilder.verify(tokenList.subList(0, 4)) && tokenList[4].type == TokenType.EQ
    }

    private fun isSimpleAssignation(tokenList: List<Token>): Boolean {
        return tokenList.size > 2 && tokenList[0].type == TokenType.IDENTIFIER && tokenList[1].type == TokenType.EQ
    }
}
