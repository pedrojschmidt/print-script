package version_0

import Position

data class Token(
    val type: TokenType,
    val value: String,
    val positionStart: Position,
    val positionEnd: Position,
) {
    override fun toString(): String {
        // Si el token es un string, un numero o un identificador, mostramos el valor
        if (type == TokenType.STRING || type == TokenType.NUMBER || type == TokenType.IDENTIFIER) {
            return "${type.name}($value)"
        }
        return type.name
    }
}
