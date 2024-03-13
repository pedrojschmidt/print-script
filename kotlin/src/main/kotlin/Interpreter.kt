class Interpreter {
    private val variables = mutableMapOf<String, String?>()
    private val stringBuffer = StringBuffer()

    fun consume(astList: List<ASTNode>): String {
        for (ast in astList) {
            when (ast) {
                is Declaration -> {
                    interpretDeclaration(ast)
                }
                is DeclarationAssignation -> {
                    interpretDeclarationAssignation(ast)
                }
                is Assignation -> {
                    interpretAssignation(ast)
                }
                is Method -> {
                    interpretMethod(ast)
                }
                else -> throw Exception("Unexpected ASTNode type")
            }
        }
        return stringBuffer.toString()
    }

    private fun interpretDeclaration(declaration: Declaration) {
        variables[declaration.identifier.getValue()] = null
    }

    private fun interpretDeclarationAssignation(declarationAssignation: DeclarationAssignation) {
        if (variables.containsKey(declarationAssignation.declaration.identifier.getValue())) {
            throw Exception("Variable ${declarationAssignation.declaration.identifier} already declared")
        }
        variables[declarationAssignation.declaration.identifier.getValue()] = declarationAssignation.assignation.value.getValue()
    }

    private fun interpretAssignation(assignation: Assignation) {
        if (!variables.containsKey(assignation.identifier.getValue())) {
            throw Exception("Variable ${assignation.identifier} not declared")
        }
        variables[assignation.identifier.getValue()] = assignation.assignation.value.getValue()
    }

    private fun interpretMethod(method: Method) {
        when (method.identifier.getType()) {
            TokenType.PRINTLN_FUNCTION -> {
                when (method.value.value.getType()) {
                    TokenType.STRING_TYPE, TokenType.NUMBER_TYPE -> {
                        stringBuffer.append(method.value.value.getValue())
                    }
                    TokenType.IDENTIFIER -> {
                        val value = variables[method.value.value.getValue()] ?: throw Exception("Variable ${method.value.value} not declared")
                        stringBuffer.append(value)
                    }
                    else -> throw Exception("Unexpected type")
                }
            }
            else -> throw Exception("Unexpected method")
        }
    }

}