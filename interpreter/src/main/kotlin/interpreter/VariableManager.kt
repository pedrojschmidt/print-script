package interpreter

import interpreter.response.VariableResponse

class VariableManager {
    private val variablesStack = mutableListOf(mutableMapOf<Variable, String?>())

    fun addVariable(
        identifier: String,
        type: String,
        isConst: Boolean,
        value: VariableResponse,
    ) {
        // Check if the variable is already declared in any scope
        if (variablesStack.any { stack -> stack.keys.any { it.identifier == identifier } }) {
            throw Exception("Variable $identifier already declared")
        }
        // Checks if the type corresponds with the value
        if (!type.equals(value.type, true)) {
            throw Exception("Type mismatch in variable $identifier assignment")
        }
        // Assign the value of the assignation to the variable
        variablesStack.last()[Variable(identifier, type, isConst)] = value.value
    }

    fun declareVariable(
        identifier: String,
        type: String,
    ) {
        // Check if the variable is already declared in any scope
        if (variablesStack.any { stack -> stack.keys.any { it.identifier == identifier } }) {
            throw Exception("Variable $identifier already declared")
        }
        // Creates a new variable with the identifier and type of the declaration, and initializes it with null value (is a map)
        variablesStack.last()[Variable(identifier, type, false)] = null
    }

    fun setVariable(
        identifier: String,
        value: VariableResponse,
    ) {
        // Look for the variable in the map
        val variable = getVariable(identifier)
        // Check if the variable is constant
        if (variable.isConst) {
            throw Exception("Variable $identifier is constant")
        }
        // Checks if the type corresponds with the value
        if (!variable.type.equals(value.type, true)) {
            throw Exception("Type mismatch in variable $identifier assignment")
        }
        variablesStack.find { it.containsKey(variable) }?.set(variable, value.value)
    }

    fun getVariableWithValue(identifier: String): Pair<Variable, String> {
        val variable = getVariable(identifier)
        val value = variablesStack.find { it.containsKey(variable) }?.get(variable)
        return Pair(variable, value!!)
    }

    fun addScope() {
        variablesStack.add(mutableMapOf())
    }

    fun removeScope() {
        variablesStack.removeLast()
    }

    fun getVariablesStack(): MutableList<MutableMap<Variable, String?>> {
        return variablesStack
    }

    fun clear() {
        variablesStack.clear()
        variablesStack.add(mutableMapOf())
    }

    private fun getVariable(identifier: String): Variable {
        for (variables in variablesStack.reversed()) {
            variables.keys.find { it.identifier == identifier }?.let { return it }
        }
        throw Exception("Variable $identifier not declared")
    }
}

data class Variable(val identifier: String, val type: String, val isConst: Boolean)
