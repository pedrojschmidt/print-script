class Token (
    private val type: TokenType,
    private val value: String,
    private val positionStart: Position,
    val positionEnd: Position,
) {

    override fun toString(): String {
        // Si el token es un string, un numero o un identificador, mostramos el valor
        if (type == TokenType.STRING || type == TokenType.NUMBER || type == TokenType.IDENTIFIER)
            return "${type.name}($value)"
        return type.name
    }

    fun getType(): TokenType {
        return type
    }

    fun getValue(): String {
        return value
    }

    fun getPositionStart(): Position {
        return positionStart
    }

}