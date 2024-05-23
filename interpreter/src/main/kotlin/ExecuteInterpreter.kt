import ast.ASTNode
import ast.Assignation
import ast.Conditional
import ast.Declaration
import ast.DeclarationAssignation
import ast.Method
import ast.SimpleAssignation
import interpreters.AssignationInterpreter
import interpreters.ConditionalInterpreter
import interpreters.DeclarationInterpreter
import interpreters.MethodInterpreter

class ExecuteInterpreter(private val interpreters: Map<Class<out ASTNode>, Interpreter>, private val utils: InterpreterUtils) {
    fun interpretAST(astList: List<ASTNode>): String? {
        for (ast in astList) {
            interpreters[ast::class.java]?.interpret(ast, utils, interpreters) ?: throw Exception("No interpreter for ${ast::class.java}")
        }
        return if (utils.getStringBuffer().isEmpty()) {
            null
        } else {
            utils.getStringBuffer().toString()
        }
    }

    companion object {
        fun getDefaultInterpreter(): ExecuteInterpreter {
            return getInterpreterByVersion("1.1")
        }

        fun getInterpreterByVersion(version: String): ExecuteInterpreter {
            val utils = InterpreterUtils()
            var visitors: Map<Class<out ASTNode>, Interpreter> = emptyMap()
            when (version) {
                "1.0" -> {
                    visitors =
                        mapOf(
                            Declaration::class.java to DeclarationInterpreter(),
                            Assignation::class.java to AssignationInterpreter(),
                            DeclarationAssignation::class.java to AssignationInterpreter(),
                            SimpleAssignation::class.java to AssignationInterpreter(),
                            Method::class.java to MethodInterpreter(),
                        )
                }
                "1.1" -> {
                    visitors =
                        mapOf(
                            Declaration::class.java to DeclarationInterpreter(),
                            Assignation::class.java to AssignationInterpreter(),
                            DeclarationAssignation::class.java to AssignationInterpreter(),
                            SimpleAssignation::class.java to AssignationInterpreter(),
                            Method::class.java to MethodInterpreter(),
                            Conditional::class.java to ConditionalInterpreter(),
                        )
                }
            }
            return ExecuteInterpreter(visitors, utils)
        }
    }
}
