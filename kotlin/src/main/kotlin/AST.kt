// TODO: Cambiar implementacion para almacenar los tokens en el AST
sealed interface ASTNode

data class Declaration(val identifier: Token, val type: Token): ASTNode
data class DeclarationAssignation(val declaration: Declaration, val assignation: Literal): ASTNode
data class Assignation(val identifier: Token, val assignation: Literal): ASTNode
// Currently only use for println()
data class Method(val identifier: Token, val value: Literal) : ASTNode
data class Literal(val value: Token) : ASTNode