data class Token_1(
    val type: TokenType_1,
    val value: String,
    val positionStart: Position,
    val positionEnd: Position,
) {
    override fun toString(): String {
        // Si el token es un string, un numero o un identificador, mostramos el valor
        if (type == TokenType_1.STRING || type == TokenType_1.NUMBER || type == TokenType_1.IDENTIFIER || type == TokenType_1.BOOLEAN) {
            return "${type.name}($value)"
        }
        return type.name
    }
}
