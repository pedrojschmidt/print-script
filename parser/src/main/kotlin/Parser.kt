class Parser(private val astBuilders: List<ASTBuilder<ASTNode>>) {

    /**
     *  TODO: Falta agregar que el parser puede recibir un statement a la vez. Habria que hacer un Token Provider que le un statement a la vez
     */
    fun generateAST(tokenList: List<Token>): ASTNode {
        for (astBuilder in astBuilders) {
            if (astBuilder.canBuild(tokenList)) {
                return astBuilder.build(tokenList)
            }
        }
        throw RuntimeException("No se pudo construir el AST")
    }

    companion object {
        fun createDefault(): Parser {
            val astBuilders = listOf(
                DeclarationASTBuilder(),
                AssignationASTBuilder(),
                MethodASTBuilder()
            )
            return Parser(astBuilders)
        }
    }
}
