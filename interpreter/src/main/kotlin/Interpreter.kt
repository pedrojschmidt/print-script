interface Interpreter {
    fun interpret(
        node: ASTNode,
        utils: InterpreterUtils,
        interpreters: Map<Class<out ASTNode>, Interpreter>,
    )
}
