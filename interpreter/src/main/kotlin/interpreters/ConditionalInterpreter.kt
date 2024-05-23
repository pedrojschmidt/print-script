package interpreters

import Interpreter
import InterpreterUtils
import ast.ASTNode
import ast.Conditional

class ConditionalInterpreter : Interpreter {
    override fun interpret(
        node: ASTNode,
        utils: InterpreterUtils,
        interpreters: Map<Class<out ASTNode>, Interpreter>,
    ) {
        if (node is Conditional) {
            val condition = utils.interpretOperation(node.condition)
            if (condition.toBoolean()) {
                utils.getVariablesStack().add(mutableMapOf())
                interpretAST(node.then, utils, interpreters)
                utils.getVariablesStack().removeLast()
            } else {
                node.otherwise?.let {
                    utils.getVariablesStack().add(mutableMapOf())
                    interpretAST(it, utils, interpreters)
                    utils.getVariablesStack().removeLast()
                }
            }
        } else {
            throw Exception("Unexpected ASTNode type")
        }
    }

    private fun interpretAST(
        astList: List<ASTNode>,
        utils: InterpreterUtils,
        interpreters: Map<Class<out ASTNode>, Interpreter>,
    ) {
        for (ast in astList) {
            interpreters[ast::class.java]?.interpret(ast, utils, interpreters) ?: throw Exception("No interpreter for ${ast::class.java}")
        }
    }
}
