class Interpreter {
    private val variablesStack = mutableListOf(mutableMapOf<Variable, String?>())
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
                is Conditional -> {
                    interpretConditional(ast)
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
        if (variablesStack.any { stack -> stack.keys.any { it.identifier == declaration.identifier } }) {
            throw Exception("Variable ${declaration.identifier} already declared")
        }
        // Creates a new variable with the identifier and type of the declaration, and initializes it with null value (is a map)
        variablesStack.last()[Variable(declaration.identifier, declaration.type, false)] = null
    }

    private fun interpretAssignation(assignation: Assignation) {
        when (assignation) {
            // Differentiate between declaration with assignation and a simple assignation
            is DeclarationAssignation -> {
                // Check if the variable is already declared in any scope
                if (variablesStack.any { stack -> stack.keys.any { it.identifier == assignation.declaration.identifier } }) {
                    throw Exception("Variable ${assignation.declaration.identifier} already declared")
                }
                // Checks if the type corresponds with the value
                if (!checkSameType(assignation.declaration.type, assignation.value)) {
                    throw Exception("Type mismatch in variable ${assignation.declaration.identifier} assignment")
                }
                // Assign the value of the assignation to the variable
                variablesStack.last()[Variable(assignation.declaration.identifier, assignation.declaration.type, assignation.isConst)] = interpretOperation(assignation.value)
            }
            is SimpleAssignation -> {
                // Look for the variable in the map
                val variable = getVariable(assignation.identifier)
                // Check if the variable is constant
                if (variable.isConst) {
                    throw Exception("Variable ${assignation.identifier} is constant")
                }
                // Checks if the type corresponds with the value
                if (checkSameType(variable.type, assignation.value)) {
                    // Assign the value of the assignation to the variable
                    variablesStack.find { it.containsKey(variable) }?.set(variable, interpretOperation(assignation.value))
                } else {
                    throw Exception("Type mismatch in variable ${assignation.identifier} assignment")
                }
            }
        }
    }

    private fun checkSameType(
        type: String,
        value: BinaryNode,
    ): Boolean {
        return when (value) {
            is StringOperator -> {
                type.equals("String", true)
            }
            is NumberOperator -> {
                type.equals("Number", true)
            }
            is BooleanOperator -> {
                type.equals("Boolean", true)
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
            is BooleanOperator -> return operation.value
            is IdentifierOperator -> {
                val variable = getVariable(operation.identifier)
                return variablesStack.find { it.containsKey(variable) }?.get(variable) ?: throw Exception("Variable ${operation.identifier} not declared")
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

    private fun interpretConditional(conditional: Conditional) {
        val condition = interpretOperation(conditional.condition)
        if (condition.toBoolean()) {
            variablesStack.add(mutableMapOf())
            interpretAST(conditional.then)
            variablesStack.removeLast()
        } else {
            conditional.otherwise?.let {
                variablesStack.add(mutableMapOf())
                interpretAST(it)
                variablesStack.removeLast()
            }
        }
    }

    private fun getVariable(identifier: String): Variable {
        for (variables in variablesStack.reversed()) {
            variables.keys.find { it.identifier == identifier }?.let { return it }
        }
        throw Exception("Variable $identifier not declared")
    }
}
