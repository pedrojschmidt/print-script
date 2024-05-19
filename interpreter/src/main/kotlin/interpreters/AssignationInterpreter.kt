package interpreters

import ASTNode
import Assignation
import DeclarationAssignation
import Interpreter
import InterpreterUtils
import SimpleAssignation
import Variable

class AssignationInterpreter : Interpreter {
    override fun interpret(
        node: ASTNode,
        utils: InterpreterUtils,
        interpreters: Map<Class<out ASTNode>, Interpreter>,
    ) {
        if (node is Assignation) {
            when (node) {
                // Differentiate between declaration with assignation and a simple assignation
                is DeclarationAssignation -> {
                    // Check if the variable is already declared in any scope
                    if (utils.getVariablesStack().any { stack -> stack.keys.any { it.identifier == node.declaration.identifier } }) {
                        throw Exception("Variable ${node.declaration.identifier} already declared")
                    }
                    // Checks if the type corresponds with the value
                    if (!utils.checkSameType(node.declaration.type, node.value)) {
                        throw Exception("Type mismatch in variable ${node.declaration.identifier} assignment")
                    }
                    // Assign the value of the assignation to the variable
                    utils.getVariablesStack().last()[Variable(node.declaration.identifier, node.declaration.type, node.isConst)] = utils.interpretOperation(node.value)
                }
                is SimpleAssignation -> {
                    // Look for the variable in the map
                    val variable = utils.getVariable(node.identifier)
                    // Check if the variable is constant
                    if (variable.isConst) {
                        throw Exception("Variable ${node.identifier} is constant")
                    }
                    // Checks if the type corresponds with the value
                    if (utils.checkSameType(variable.type, node.value)) {
                        // Assign the value of the assignation to the variable
                        utils.getVariablesStack().find { it.containsKey(variable) }?.set(variable, utils.interpretOperation(node.value))
                    } else {
                        throw Exception("Type mismatch in variable ${node.identifier} assignment")
                    }
                }
            }
        } else {
            throw Exception("Unexpected ASTNode type")
        }
    }
}
