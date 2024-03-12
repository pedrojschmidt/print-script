
sealed class ASTNode


sealed class StatementNode : ASTNode()
sealed class ExpressionNode : ASTNode()

data class RootNode(val statements: List<StatementNode>) : ASTNode()
data class IdentifierNode(val value: String) : ASTNode()


data class AssignmentNode(val identifier: IdentifierNode, val expression: ExpressionNode) : StatementNode()
// Puede ser que el PrintlnNode sea un ExpressionNode o un IdentifierNode
data class PrintlnNode(val content: ASTNode) : StatementNode()


data class StringNode(val value: String) : ExpressionNode()
data class NumberNode(val value: Double) : ExpressionNode()
