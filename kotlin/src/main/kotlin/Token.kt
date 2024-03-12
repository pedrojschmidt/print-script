class Token (
    private val type: TokenType,
    private val value: String,
    val positionStart: Position,
    val positionEnd: Position,
) {

    override fun toString(): String {
        if (type == TokenType.STRING || type == TokenType.NUMBER || type == TokenType.IDENTIFIER)
            return "${type.name}($value)"
        return type.name
    }

}