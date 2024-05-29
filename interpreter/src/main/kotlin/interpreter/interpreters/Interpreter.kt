package interpreter.interpreters

import ast.ASTNode
import interpreter.VariableManager
import interpreter.response.InterpreterResponse

interface Interpreter<T : ASTNode> {
    fun interpret(
        astNode: T,
        variableManager: VariableManager,
    ): InterpreterResponse
}
