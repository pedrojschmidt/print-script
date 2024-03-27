class Interpreter {
    private val variables = mutableMapOf<Variable, String?>()
    private val stringBuffer = StringBuffer()

    fun consume(astList: List<ASTNode>): String {
        for (ast in astList) {
            when (ast) {
                is Declaration -> {
                    interpretDeclaration(ast)
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
        variables[Variable(declaration.identifier, declaration.type)] = null
    }

    private fun interpretAssignation(assignation: Assignation) {
        when (assignation) {
            is DeclarationAssignation -> {
                if (variables.keys.any { it.identifier == assignation.declaration.identifier }) {
                    throw Exception("Variable ${assignation.declaration.identifier} already declared")
                }
                variables[Variable(assignation.declaration.identifier, assignation.declaration.type)]= interpretOperation(assignation.assignation)
            }
            is SimpleAssignation -> {
                if (checkSameType(assignation.identifier, assignation.assignation)) {
                    val variable = variables.keys.find { it.identifier == assignation.identifier } ?: throw Exception("Variable ${assignation.identifier} not declared")
                    variables[variable] = interpretOperation(assignation.assignation)
                } else {
                    throw Exception("Type mismatch in variable ${assignation.identifier} assignment")
                }
            }
        }
    }

    private fun checkSameType(identifier: String, operation: BinaryNode): Boolean {
        val variable = variables.keys.find { it.identifier == identifier } ?: throw Exception("Variable $identifier not declared")
        return when (operation) {
            is StringOperator -> {
                variable.type.equals("String", true)
            }
            is NumberOperator -> {
                variable.type.equals("Number", true)
            }
            else -> {
                throw Exception("Unexpected operation")
            }
        }
    }

    private fun interpretOperation(operation: BinaryNode): String {
        return when (operation) {
            is StringOperator -> return operation.value
            is NumberOperator -> return operation.value.toString()
            is IdentifierOperator -> {
                val variable = variables.keys.find { it.identifier == operation.identifier }
                    ?: throw Exception("Variable ${operation.identifier} not declared")
                return variables[variable] ?: throw Exception("Variable ${operation.identifier} not initialized")
            }

            is BinaryOperation -> {
                val left = interpretOperation(operation.left)
                val right = interpretOperation(operation.right)

                when (operation.symbol) {
                    "+" -> {
                        return if (left.toDoubleOrNull() != null && right.toDoubleOrNull() != null) {
                            (left.toDouble() + right.toDouble()).toString()
                        } else {
                            // Si al menos uno es String, los concatena
                            "$left$right"
                        }
                    }

                    "-" -> {
                        return if (left.toDoubleOrNull() != null && right.toDoubleOrNull() != null) {
                            (left.toDouble() - right.toDouble()).toString()
                        } else {
                            throw IllegalArgumentException("Unsupported operation: '-' with non-numeric operands.")
                        }
                    }

                    "*" -> {
                        return if (left.toDoubleOrNull() != null && right.toDoubleOrNull() != null) {
                            (left.toDouble() * right.toDouble()).toString()
                        } else {
                            throw IllegalArgumentException("Unsupported operation: '*' with non-numeric operands.")
                        }
                    }

                    "/" -> {
                        if (left.toDoubleOrNull() != null && right.toDoubleOrNull() != null) {
                            (left.toDouble() / right.toDouble()).toString()
                        } else {
                            throw IllegalArgumentException("Unsupported operation: '/' with non-numeric operands.")
                        }
                    }
                    else -> throw Exception("${operation.symbol} is not a valid operation")
                }
            }
            else -> throw Exception("Unexpected binary operation node")
        }
    }

    private fun interpretMethod(method: Method) {
        when (method.identifier.lowercase()) {
            "println" -> {
                stringBuffer.append(interpretOperation(method.value))
                stringBuffer.append("\n")
            }
            else -> throw Exception("Unexpected method")
        }
    }

    public fun getMap(): MutableMap<Variable, String?> {
        return variables;
    }
}