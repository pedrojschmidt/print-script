sealed interface ASTNode

// Esta interfaz sirve para que se pueda hacer pattern matching con los dos tipos de asignaciones
sealed interface Assignation : ASTNode

sealed interface BinaryNode

// let x: number;
data class Declaration(val identifier: String, val type: String) : ASTNode

// let x: number = 5;
data class DeclarationAssignation(val declaration: Declaration, val assignation: BinaryNode) : Assignation

// x = 5 + 5;
data class SimpleAssignation(val identifier: String, val assignation: BinaryNode) : Assignation

// println(x);
data class Method(val identifier: String, val value: BinaryNode) : ASTNode

// NodeL(value) (+ - * /) NodeR(value)
data class BinaryOperation(val left: BinaryNode, val symbol: String, val right: BinaryNode) : BinaryNode

// Representa cualquier String, como "Hello"
data class StringOperator(val value: String) : BinaryNode

// Representa cualquier número, como 5
data class NumberOperator(val value: Double) : BinaryNode

// Representa cualquier identificador, como x
data class IdentifierOperator(val identifier: String) : BinaryNode
