package interpreters

import Interpreter
import InterpreterUtils
import ast.ASTNode
import ast.Method

class MethodInterpreter : Interpreter {
    override fun interpret(
        node: ASTNode,
        utils: InterpreterUtils,
        interpreters: Map<Class<out ASTNode>, Interpreter>,
    ) {
        if (node is Method) {
            when (node.identifier.lowercase()) {
                "println" -> {
                    utils.getStringBuffer().append(utils.interpretOperation(node.value))
                    utils.getStringBuffer().append("\n")
                }
                else -> throw Exception("Unexpected method")
            }
        } else {
            throw Exception("Unexpected ASTNode type")
        }
    }
}
