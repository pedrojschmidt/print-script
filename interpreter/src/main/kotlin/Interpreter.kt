class Interpreter {
    private val variables = mutableMapOf<Variable, String?>()
    private val stringBuffer = StringBuffer()

    fun interpretAST(astList: List<ASTNode>): String? {
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

        return if (stringBuffer.isEmpty()) {
            null
        } else {
            stringBuffer.toString()
        }
    }

    private fun interpretDeclaration(declaration: Declaration) {
        // Check if the variable is already declared
        if (variables.keys.any { it.identifier == declaration.identifier }) {
            throw Exception("Variable ${declaration.identifier} already declared")
        }
        // Creates a new variable with the identifier and type of the declaration, and initializes it with null value (is a map)
        variables[Variable(declaration.identifier, declaration.type)] = null
    }

    private fun interpretAssignation(assignation: Assignation) {
        when (assignation) {
            // Differentiate between declaration with assignation and a simple assignation
            is DeclarationAssignation -> {
                // Check if the variable is already declared
                if (variables.keys.any { it.identifier == assignation.declaration.identifier }) {
                    throw Exception("Variable ${assignation.declaration.identifier} already declared")
                }
                // Checks if the type corresponds with the value
                if (!checkSameType(assignation.declaration.type, assignation.value)) {
                    throw Exception("Type mismatch in variable ${assignation.declaration.identifier} assignment")
                }
                // Assign the value of the assignation to the variable
                variables[Variable(assignation.declaration.identifier, assignation.declaration.type)] = interpretOperation(assignation.value)
            }
            is SimpleAssignation -> {
                // Look for the variable in the map
                val variable = getVariable(assignation.identifier)
                // Checks if the type corresponds with the value
                if (checkSameType(variable.type, assignation.value)) {
                    // Assign the value of the assignation to the variable
                    variables[variable] = interpretOperation(assignation.value)
                } else {
                    throw Exception("Type mismatch in variable ${assignation.identifier} assignment")
                }
            }
        }
    }

    private fun checkSameType(type: String, value: BinaryNode): Boolean {
        return when (value) {
            is StringOperator -> {
                type.equals("String", true)
            }
            is NumberOperator -> {
                type.equals("Number", true)
            }
            is IdentifierOperator -> {
                type.equals(getVariable(value.identifier).type, true)
            }
            is BinaryOperation -> {
                if (value.symbol == "+" && (checkSameType("String", value.left) || checkSameType("String", value.right))) {
                    return type.equals("String", true)
                }
                checkSameType(type, value.left) && checkSameType(type, value.right)
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
                return variables[getVariable(operation.identifier)] ?: throw Exception("Variable ${operation.identifier} not initialized")
            }
            is BinaryOperation -> {
                val left = interpretOperation(operation.left)
                val right = interpretOperation(operation.right)

                when (operation.symbol) {
                    "+" -> {
                        return if (left.toDoubleOrNull() != null && right.toDoubleOrNull() != null) {
                            val numberValue = left.toDouble() + right.toDouble()
                            if (numberValue % 1 == 0.0) {
                                numberValue.toInt().toString()
                            } else {
                                numberValue.toString()
                            }
                        } else {
                            // Si al menos uno es String, los concatena
                            "$left$right"
                        }
                    }

                    "-" -> {
                        return if (left.toDoubleOrNull() != null && right.toDoubleOrNull() != null) {
                            val numberValue = left.toDouble() - right.toDouble()
                            if (numberValue % 1 == 0.0) {
                                numberValue.toInt().toString()
                            } else {
                                numberValue.toString()
                            }
                        } else {
                            throw IllegalArgumentException("Unsupported operation: '-' with non-numeric operands.")
                        }
                    }

                    "*" -> {
                        return if (left.toDoubleOrNull() != null && right.toDoubleOrNull() != null) {
                            val numberValue = left.toDouble() * right.toDouble()
                            if (numberValue % 1 == 0.0) {
                                numberValue.toInt().toString()
                            } else {
                                numberValue.toString()
                            }
                        } else {
                            throw IllegalArgumentException("Unsupported operation: '*' with non-numeric operands.")
                        }
                    }

                    "/" -> {
                        if (left.toDoubleOrNull() != null && right.toDoubleOrNull() != null) {
                            val numberValue = left.toDouble() / right.toDouble()
                            if (numberValue % 1 == 0.0) {
                                numberValue.toInt().toString()
                            } else {
                                numberValue.toString()
                            }
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

    private fun getVariable(identifier: String): Variable {
        return variables.keys.find { it.identifier == identifier }
            ?: throw Exception("Variable $identifier not declared")
    }
}
