class Interpreter {
    private val variables = mutableMapOf<String, String?>()
    private val stringBuffer = StringBuffer()
    fun execute(ast: List<ASTNode>): String {
        for (node in ast) consume(node)
        return stringBuffer.toString()
    }

    private fun consume(astNode: ASTNode) {
        when (astNode) {
            is Declaration -> {
                variables[astNode.identifier.getValue()] = null
            }
            is DeclarationAssignation -> {
                if (variables.containsKey(astNode.declaration.identifier.getValue())) {
                    throw Exception("Variable ${astNode.declaration.identifier} already declared")
                }
                variables[astNode.declaration.identifier.getValue()] = astNode.assignation.value.getValue()
            }
            is Assignation -> {
                if (!variables.containsKey(astNode.identifier.getValue())) {
                    throw Exception("Variable ${astNode.identifier} not declared")
                }
                variables[astNode.identifier.getValue()] = astNode.assignation.value.getValue()
            }
            is Method -> {
                 when (astNode.identifier.getType()) {
                    TokenType.PRINTLN_FUNCTION -> {
                        when (astNode.value.value.getType()) {
                            TokenType.STRING_TYPE, TokenType.NUMBER_TYPE -> {
                                stringBuffer.append(astNode.value.value.getValue())
                            }
                            TokenType.IDENTIFIER -> {
                                val value = variables[astNode.value.value.getValue()] ?: throw Exception("Variable ${astNode.value.value} not declared")
                                stringBuffer.append(value)
                            }
                            else -> throw Exception("Unexpected type")
                        }
                    }
                    else -> throw Exception("Unexpected method")
                }
            }
            else -> throw Exception("Unexpected ASTNode type")
        }
    }
}