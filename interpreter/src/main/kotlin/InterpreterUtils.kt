class InterpreterUtils {
    private val variablesStack = mutableListOf(mutableMapOf<Variable, String?>())
    private val stringBuffer = StringBuffer()

    fun interpretOperation(operation: ValueNode): String {
        return when (operation) {
            is StringOperator -> return operation.value
            is NumberOperator -> return operation.value.toString()
            is BooleanOperator -> return operation.value
            is IdentifierOperator -> {
                val variable = getVariable(operation.identifier)
                return variablesStack.find { it.containsKey(variable) }?.get(variable) ?: throw Exception("Variable ${operation.identifier} not declared")
            }
            is Method -> {
                when (operation.identifier.lowercase()) {
                    "readenv" -> {
                        val argument =
                            (operation.value as? StringOperator)?.value
                                ?: throw IllegalArgumentException("readEnv requires a string argument")
                        System.getenv(argument) ?: throw IllegalArgumentException("Environment variable $argument does not exist")
                    }
                    "readinput" -> {
                        val argument =
                            (operation.value as? StringOperator)?.value
                                ?: throw IllegalArgumentException("readInput requires a string argument")
                        println(argument) // Print the prompt
                        readLine() ?: throw IllegalArgumentException("Failed to read input")
                    }
                    else -> throw IllegalArgumentException("Unsupported method: ${operation.identifier}")
                }
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

    fun checkSameType(
        type: String,
        value: ValueNode,
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

    fun getVariable(identifier: String): Variable {
        for (variables in variablesStack.reversed()) {
            variables.keys.find { it.identifier == identifier }?.let { return it }
        }
        throw Exception("Variable $identifier not declared")
    }

    fun getVariablesStack(): MutableList<MutableMap<Variable, String?>> {
        return variablesStack
    }

    fun getStringBuffer(): StringBuffer {
        return stringBuffer
    }
}
