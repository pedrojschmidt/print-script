import builder.ASTBuilder
import builder.AssignationASTBuilder
import builder.DeclarationASTBuilder
import builder.MethodASTBuilder

class Parser(private val astBuilders: List<ASTBuilder<ASTNode>>) {
    fun generateAST(tokens: List<Token>): ASTNode? {
        for (astBuilder in astBuilders) {
            if (removeEmptyLines(tokens).isEmpty()) {
                return null
            }
            if (astBuilder.verify(removeEmptyLines(tokens))) {
                return astBuilder.build(removeEmptyLines(tokens))
            }
        }
        throw RuntimeException("No se pudo construir el AST")
    }

    private fun removeEmptyLines(tokens: List<Token>): List<Token> {
        return tokens.filter { it.type != TokenType.NEW_LINE }
    }

    companion object {
        fun getDefaultParser(): Parser {
            return Parser(
                listOf(
                    DeclarationASTBuilder(),
                    AssignationASTBuilder(),
                    MethodASTBuilder(),
                ),
            )
        }
    }
}
