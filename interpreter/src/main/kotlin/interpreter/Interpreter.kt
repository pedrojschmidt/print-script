import ast.ASTNode
import interpreter.InterpreterUtils

interface Interpreter {
    fun interpret(
        node: ASTNode,
        utils: InterpreterUtils,
        interpreters: Map<Class<out ASTNode>, Interpreter>,
    )
}
