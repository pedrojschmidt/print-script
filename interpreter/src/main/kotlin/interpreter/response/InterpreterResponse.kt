package interpreter.response

interface InterpreterResponse

data class SuccessResponse(val message: String?) : InterpreterResponse

data class ErrorResponse(val message: String) : InterpreterResponse

data class VariableResponse(val type: String, val value: String) : InterpreterResponse
