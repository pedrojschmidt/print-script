package interpreter.interpreters

import Interpreter
import Variable
import ast.ASTNode
import ast.Declaration
import interpreter.InterpreterUtils

class DeclarationInterpreter : Interpreter {
    override fun interpret(
        node: ASTNode,
        utils: InterpreterUtils,
        interpreters: Map<Class<out ASTNode>, Interpreter>,
    ) {
        if (node is Declaration) {
            // Check if the variable is already declared
            if (utils.getVariablesStack().any { stack -> stack.keys.any { it.identifier == node.identifier } }) {
                throw Exception("Variable ${node.identifier} already declared")
            }
            // Creates a new variable with the identifier and type of the declaration, and initializes it with null value (is a map)
            utils.getVariablesStack().last()[Variable(node.identifier, node.type, false)] = null
        } else {
            throw Exception("Unexpected ASTNode type")
        }
    }
}
