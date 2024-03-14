sealed interface ASTNode
sealed interface Assignation: ASTNode
sealed interface BinaryNode

data class Declaration(val identifier: String, val type: String): ASTNode
data class DeclarationAssignation(val declaration: Declaration, val assignation: BinaryNode): Assignation
data class SimpleAssignation(val identifier: String, val assignation: BinaryNode): Assignation
data class Method(val identifier: String, val value: BinaryNode) : ASTNode
data class BinaryOperation(val left: BinaryNode, val symbol: String, val right: BinaryNode): BinaryNode
data class StringOperator(val value: String): BinaryNode
data class NumberOperator(val value: Double): BinaryNode
data class IdentifierOperator(val identifier: String): BinaryNode