data class Token1(
    val type: TokenType1,
    val value: String,
    val positionStart: Position,
    val positionEnd: Position,
) {
    override fun toString(): String {
        // Si el token es un string, un numero o un identificador, mostramos el valor
        if (type == TokenType1.STRING || type == TokenType1.NUMBER || type == TokenType1.IDENTIFIER || type == TokenType1.BOOLEAN) {
            return "${type.name}($value)"
        }
        return type.name
    }
}
