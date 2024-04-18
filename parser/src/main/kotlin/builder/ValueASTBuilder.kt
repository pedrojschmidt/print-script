package builder

import BinaryNode
import BinaryOperation
import IdentifierOperator
import NumberOperator
import StringOperator
import Token

class ValueASTBuilder : ASTBuilder<BinaryNode> {
    override fun verify(statement: List<Token>): Boolean {
        return statement.isNotEmpty()
    }

    override fun build(statement: List<Token>): BinaryNode {
        val filteredStatement = filterTokens(statement, listOf(TokenType.NEW_LINE, TokenType.SEMICOLON))
        val (node, remainingTokens) = buildTerm(filteredStatement)
        if (remainingTokens.isNotEmpty()) {
            throw RuntimeException("Unexpected tokens remaining after building expression: " + remainingTokens.joinToString(", ") { it.toString() })
        }
        return node
    }

    private fun buildTerm(tokens: List<Token>): Pair<BinaryNode, List<Token>> {
        var (node, remainingTokens) = buildFactor(tokens)
        while (verifyRemainingTokens(remainingTokens)) {
            val operatorToken = remainingTokens[0]
            val (rightNode, newRemainingTokens) = buildFactor(remainingTokens.subList(1, remainingTokens.size))
            node = BinaryOperation(node, operatorToken.value, rightNode)
            remainingTokens = newRemainingTokens
        }
        return Pair(node, remainingTokens)
    }

    private fun buildFactor(tokens: List<Token>): Pair<BinaryNode, List<Token>> {
        val firstToken = tokens[0]
        return when (firstToken.type) {
            TokenType.LPAREN -> {
                val correspondingRParenIndex = findCorrespondingRParenIndex(tokens)
                val innerExpression = tokens.subList(1, correspondingRParenIndex)
                val innerNode = build(innerExpression)
                Pair(innerNode, tokens.subList(correspondingRParenIndex + 1, tokens.size))
            }
            TokenType.NUMBER -> {
                val numberValue = firstToken.value.toDouble()
                // Diferenciar entre enteros y decimales
                if (numberValue % 1 == 0.0) {
                    Pair(NumberOperator(numberValue.toInt()), tokens.subList(1, tokens.size))
                } else {
                    Pair(NumberOperator(numberValue), tokens.subList(1, tokens.size))
                }
            }
            TokenType.STRING -> {
                if (tokens.size > 2 && tokens[1].type == TokenType.PLUS && tokens[2].type == TokenType.NUMBER) {
                    Pair(BinaryOperation(StringOperator(firstToken.value), tokens[1].value, StringOperator(tokens[2].value)), tokens.subList(3, tokens.size))
                } else {
                    Pair(StringOperator(firstToken.value), tokens.subList(1, tokens.size))
                }
            }
            TokenType.IDENTIFIER -> Pair(IdentifierOperator(firstToken.value), tokens.subList(1, tokens.size))
            else -> throw RuntimeException("Unexpected token type: ${firstToken.type} at ${firstToken.positionStart.y}:${firstToken.positionStart.x}")
        }
    }

    private fun findCorrespondingRParenIndex(tokenList: List<Token>): Int {
        var openParenthesesCount = 1
        for (i in 1 until tokenList.size) {
            when (tokenList[i].type) {
                TokenType.LPAREN -> openParenthesesCount++
                TokenType.RPAREN -> {
                    openParenthesesCount--
                    if (openParenthesesCount == 0) {
                        return i
                    }
                }
            }
        }
        throw RuntimeException("Mismatched parentheses in expression")
    }

    private fun verifyRemainingTokens(remainingTokens: List<Token>): Boolean {
        return if (remainingTokens.size > 1) {
            remainingTokens[0].type in listOf(TokenType.PLUS, TokenType.MINUS, TokenType.TIMES, TokenType.DIV)
        } else {
            if (remainingTokens.isNotEmpty()) {
                remainingTokens[0].type in listOf(TokenType.NUMBER, TokenType.STRING, TokenType.IDENTIFIER)
            }
            false
        }
    }
}
