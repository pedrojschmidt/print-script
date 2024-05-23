package parser.builder

import ast.BinaryOperation
import ast.BooleanOperator
import ast.IdentifierOperator
import ast.Method
import ast.NumberOperator
import ast.StringOperator
import ast.ValueNode
import token.Token
import token.TokenType

class ValueASTBuilder : ASTBuilder<ValueNode> {
    override fun verify(statement: List<Token>): Boolean {
        return statement.isNotEmpty()
    }

    override fun build(statement: List<Token>): ValueNode {
        val filteredStatement = filterTokens(statement, listOf(TokenType.NEW_LINE, TokenType.SEMICOLON))
        val rpnTokens = shuntingYard(filteredStatement)
        val nodeStack = ArrayDeque<ValueNode>()

        var i = 0
        while (i < rpnTokens.size) {
            val token = rpnTokens[i]
            when (token.type) {
                TokenType.NUMBER -> {
                    nodeStack.addLast(NumberOperator(if (token.value.toDouble() % 1 == 0.0) token.value.toInt() else token.value.toDouble()))
                }
                TokenType.STRING -> nodeStack.addLast(StringOperator(token.value))
                TokenType.BOOLEAN_TYPE -> nodeStack.addLast(BooleanOperator(token.value))
                TokenType.IDENTIFIER -> nodeStack.addLast(IdentifierOperator(token.value))
                TokenType.PLUS -> {
                    val rightNode = nodeStack.removeLast()
                    val leftNode = nodeStack.removeLast()
                    if (leftNode is StringOperator && rightNode is NumberOperator) {
                        nodeStack.addLast(BinaryOperation(leftNode, token.value, StringOperator(rightNode.value.toString())))
                    } else if (leftNode is NumberOperator && rightNode is StringOperator) {
                        nodeStack.addLast(BinaryOperation(StringOperator(leftNode.value.toString()), token.value, rightNode))
                    } else {
                        nodeStack.addLast(BinaryOperation(leftNode, token.value, rightNode))
                    }
                }
                TokenType.MINUS, TokenType.TIMES, TokenType.DIV -> {
                    val rightNode = nodeStack.removeLast()
                    val leftNode = nodeStack.removeLast()
                    nodeStack.addLast(BinaryOperation(leftNode, token.value, rightNode))
                }
                TokenType.READENV_FUNCTION, TokenType.READINPUT_FUNCTION -> {
                    val functionName = token.value
                    i += 2 // Skip the opening parenthesis
                    val argumentValue = rpnTokens[i].value
                    nodeStack.addLast(Method(functionName, StringOperator(argumentValue)))
                    i += 2 // Skip the closing parenthesis
                }
                else -> throw RuntimeException("Unexpected token type: ${token.type} at ${token.positionStart.y}:${token.positionStart.x}")
            }
            i++
        }

        if (nodeStack.size != 1) {
            throw RuntimeException("Invalid expression: more than one node remaining in stack after parsing")
        }

        return nodeStack.first()
    }

    private fun shuntingYard(tokens: List<Token>): List<Token> {
        val outputQueue = ArrayDeque<Token>()
        val operatorStack = ArrayDeque<Token>()

        var i = 0
        while (i < tokens.size) {
            val token = tokens[i]
            when (token.type) {
                TokenType.NUMBER, TokenType.STRING, TokenType.BOOLEAN_TYPE, TokenType.IDENTIFIER -> outputQueue.addLast(token)
                TokenType.PLUS, TokenType.MINUS, TokenType.TIMES, TokenType.DIV -> {
                    while (operatorStack.isNotEmpty() && operatorStack.last().type != TokenType.LPAREN && precedence[operatorStack.last().value]!! >= precedence[token.value]!!) {
                        outputQueue.addLast(operatorStack.removeLast())
                    }
                    operatorStack.addLast(token)
                }
                TokenType.LPAREN -> operatorStack.addLast(token)
                TokenType.RPAREN -> {
                    while (operatorStack.isNotEmpty() && operatorStack.last().type != TokenType.LPAREN) {
                        outputQueue.addLast(operatorStack.removeLast())
                    }
                    if (operatorStack.isEmpty() || operatorStack.removeLast().type != TokenType.LPAREN) {
                        throw RuntimeException("Mismatched parentheses in expression")
                    }
                }
                TokenType.READENV_FUNCTION, TokenType.READINPUT_FUNCTION -> {
                    outputQueue.addLast(token)
                    i++
                    while (tokens[i].type != TokenType.RPAREN) {
                        outputQueue.addLast(tokens[i])
                        i++
                    }
                    outputQueue.addLast(tokens[i]) // Add the closing parenthesis
                }
                else -> throw RuntimeException("Unexpected token type: ${token.type} at ${token.positionStart.y}:${token.positionStart.x}")
            }
            i++
        }

        while (operatorStack.isNotEmpty()) {
            val operator = operatorStack.removeLast()
            if (operator.type == TokenType.LPAREN || operator.type == TokenType.RPAREN) {
                throw RuntimeException("Mismatched parentheses in expression")
            }
            outputQueue.addLast(operator)
        }

        return outputQueue.toList()
    }

    private val precedence =
        mapOf(
            "+" to 1,
            "-" to 1,
            "*" to 2,
            "/" to 2,
        )
}
