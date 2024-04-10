package builder

import BinaryNode
import BinaryOperation
import IdentifierOperator
import NumberOperator
import StringOperator
import Token
import TokenType

class ContentASTBuilder : ASTBuilder<BinaryNode> {
    override fun verify(tokens: List<Token>): Boolean {
        return tokens.isNotEmpty()
    }

    override fun build(tokens: List<Token>): BinaryNode {
        val (node, remainingTokens) = buildTerm(tokens)
        if (remainingTokens.isNotEmpty()) {
            throw RuntimeException("Unexpected tokens remaining after building expression")
        }
        return node
    }

    private fun buildTerm(tokens: List<Token>): Pair<BinaryNode, List<Token>> {
        var (node, remainingTokens) = buildFactor(tokens)
        while (remainingTokens.isNotEmpty() && (remainingTokens[0].type == TokenType.TIMES || remainingTokens[0].type == TokenType.DIV
             || remainingTokens[0].type == TokenType.PLUS || remainingTokens[0].type == TokenType.MINUS)) {
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
            TokenType.NUMBER -> Pair(NumberOperator(firstToken.value.toDouble()), tokens.subList(1, tokens.size))
            TokenType.STRING -> {
                if (tokens.size > 2 && tokens[1].type == TokenType.PLUS && tokens[2].type == TokenType.NUMBER) {
                    Pair(BinaryOperation(StringOperator(firstToken.value), tokens[1].value, StringOperator(tokens[2].value)), tokens.subList(3, tokens.size))
                } else {
                    Pair(StringOperator(firstToken.value), tokens.subList(1, tokens.size))
                }
            }
            TokenType.IDENTIFIER -> Pair(IdentifierOperator(firstToken.value), tokens.subList(1, tokens.size))
            else -> throw RuntimeException("Unexpected token type in factor: ${firstToken.type}")
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
}
