package builder

import ASTNode
import Conditional
import Parser
import Token
import java.util.Stack

class ConditionalASTBuilder(private val version: String) : ASTBuilder<Conditional> {
    private val valueASTBuilder = ValueASTBuilder()

    override fun verify(statement: List<Token>): Boolean {
        if (version == "1.0") {
            return false
        }
        val filteredStatement = filterTokens(statement, listOf(TokenType.NEW_LINE))
        if (filteredStatement.isEmpty()) {
            return false
        }
        if (filteredStatement[0].type == TokenType.IF_KEYWORD && filteredStatement[1].type == TokenType.LPAREN &&
            filteredStatement[3].type == TokenType.RPAREN && filteredStatement[4].type == TokenType.LBRACE
        ) {
            if (!verifyContainsElseStatement(filteredStatement)) {
                return if (filteredStatement[filteredStatement.size - 1].type == TokenType.RBRACE) {
                    valueASTBuilder.verify(filteredStatement.subList(5, filteredStatement.size - 2))
                } else {
                    false
                }
            } else {
                val elseIndex = filteredStatement.indexOfFirst { it.type == TokenType.ELSE_KEYWORD }
                return if (filteredStatement[elseIndex - 1].type == TokenType.RBRACE && filteredStatement[elseIndex + 1].type == TokenType.LBRACE) {
                    valueASTBuilder.verify(filteredStatement.subList(5, elseIndex - 1)) &&
                        valueASTBuilder.verify(filteredStatement.subList(elseIndex + 2, filteredStatement.size - 2))
                } else {
                    false
                }
            }
        }
        return false
    }

    override fun build(statement: List<Token>): Conditional {
        val filteredStatement = filterTokens(statement, listOf(TokenType.NEW_LINE))
        if (!verifyContainsElseStatement(filteredStatement)) {
            return Conditional(
                valueASTBuilder.build(filteredStatement.subList(2, 3)),
                buildConditionalBody(filteredStatement.subList(5, filteredStatement.size - 1)),
                null,
            )
        } else {
            val elseIndex = filteredStatement.indexOfFirst { it.type == TokenType.ELSE_KEYWORD }
            return Conditional(
                valueASTBuilder.build(filteredStatement.subList(2, 3)),
                buildConditionalBody(filteredStatement.subList(5, elseIndex - 1)),
                buildConditionalBody(filteredStatement.subList(elseIndex + 2, filteredStatement.size - 1)),
            )
        }
    }

    private fun buildConditionalBody(filteredStatement: List<Token>): List<ASTNode> {
        val astNodes = mutableListOf<ASTNode?>()
        val parser = Parser.getDefaultParser()
        val stack = Stack<Token>()
        val statements = mutableListOf<List<Token>>()
        var braceCount = 0

        for (token in filteredStatement) {
            when (token.type) {
                TokenType.LBRACE -> {
                    stack.push(token)
                    braceCount++
                }
                TokenType.RBRACE -> {
                    braceCount--
                    if (braceCount < 0) {
                        throw IllegalArgumentException("Unbalanced braces in the statement")
                    }
                    stack.push(token)
                    if (braceCount == 0) {
                        val statement = mutableListOf<Token>()
                        while (stack.isNotEmpty()) {
                            statement.add(0, stack.pop())
                        }
                        statements.add(statement)
                    }
                }
                TokenType.SEMICOLON -> {
                    stack.push(token)
                    if (braceCount == 0) {
                        val statement = mutableListOf<Token>()
                        while (stack.isNotEmpty()) {
                            statement.add(0, stack.pop())
                        }
                        statements.add(statement)
                    }
                }
                else -> stack.push(token)
            }
        }

        // handle any remaining tokens in the stack
        if (stack.isNotEmpty() || braceCount != 0) {
            throw Exception("Malformed statement, unbalanced braces or missing semicolon")
        }

        // generate AST for each statement
        for (statement in statements) {
            astNodes.add(parser.generateAST(statement))
        }
        return astNodes.filterNotNull()
    }

    private fun verifyContainsElseStatement(filteredStatement: List<Token>): Boolean {
        // Checks if there is only one else token type in any position
        return filteredStatement.count { it.type == TokenType.ELSE_KEYWORD } == 1
    }
}
