package interpreter.interpreters

import ast.Declaration
import interpreter.VariableManager
import interpreter.response.ErrorResponse
import interpreter.response.InterpreterResponse
import interpreter.response.SuccessResponse

class DeclarationInterpreter : Interpreter<Declaration> {
    override fun interpret(
        astNode: Declaration,
        variableManager: VariableManager,
    ): InterpreterResponse {
        return try {
            variableManager.declareVariable(astNode.identifier, astNode.type)
            SuccessResponse(null)
        } catch (e: Exception) {
            ErrorResponse(e.message ?: "Error while declaring variable ${astNode.identifier}")
        }
    }
}
