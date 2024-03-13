class Interpreter {
    private val variables = mutableMapOf<String, String>()
    fun execute(rootNode: RootNode): String {
        val buffer = StringBuffer()
        for (statement in rootNode.statements) {
            buffer.append(consume(statement))
        }
        return buffer.toString()
    }

    private fun consume(astNode: ASTNode) {
        when (astNode) {
            is AssignmentNode -> {
                when(astNode.expression) {
                    is StringNode -> variables[astNode.identifier.value] = astNode.expression.value
                    is NumberNode -> variables[astNode.identifier.value] = astNode.expression.value.toString()
                }
            }
            is PrintlnNode -> {
                when(astNode.content) {
                    is StringNode -> println(astNode.content.value)
                    is NumberNode -> println(astNode.content.value)
                    is IdentifierNode -> println(variables[astNode.content.value])
                    else -> throw Exception("Unexpected ASTNode type")
                }
            }
            else -> throw Exception("Unexpected ASTNode type")
        }
    }
}