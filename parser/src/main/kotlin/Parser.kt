import builder.ASTBuilder
import builder.AssignationASTBuilder
import builder.ConditionalASTBuilder
import builder.DeclarationASTBuilder
import builder.MethodASTBuilder

class Parser(private val astBuilders: List<ASTBuilder<ASTNode>>) {
    fun generateAST(tokens: List<Token>): ASTNode? {
        if (tokens.isNotEmpty()) {
            for (astBuilder in astBuilders) {
                if (astBuilder.verify((tokens))) {
                    return astBuilder.build((tokens))
                }
            }
        }
        return null
    }

    companion object {
        fun getDefaultParser(): Parser {
            return Parser(
                listOf(
                    DeclarationASTBuilder(),
                    AssignationASTBuilder(),
                    MethodASTBuilder(),
                    ConditionalASTBuilder(),
                ),
            )
        }
    }
}
