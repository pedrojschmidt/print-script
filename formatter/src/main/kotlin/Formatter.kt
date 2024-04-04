import org.yaml.snakeyaml.Yaml

class Formatter(private val formatRules: FormatRules) {
    companion object {
        fun fromYaml(yamlContent: String): Formatter {
            val yaml = Yaml()
            val yamlMap = yaml.load(yamlContent) as Map<String, Map<String, Any>>
            val rulesMap = yamlMap["rules"] ?: throw IllegalArgumentException("Invalid YAML content")
            val spaceBeforeColon = rulesMap["spaceBeforeColon"] as Boolean
            val spaceAfterColon = rulesMap["spaceAfterColon"] as Boolean
            val spaceAroundAssignment = rulesMap["spaceAroundAssignment"] as Boolean
            val newlineBeforePrintln = (rulesMap["newlineBeforePrintln"] as Int?) ?: 0
            val spaceAfterLet = rulesMap["spaceAfterLet"] as Boolean
            val stringFormat = rulesMap["stringFormat"] as Boolean

            val formatRules =
                FormatRules(
                    spaceBeforeColon,
                    spaceAfterColon,
                    spaceAroundAssignment,
                    newlineBeforePrintln,
                    spaceAfterLet,
                    stringFormat,
                )
            return Formatter(formatRules)
        }
    }

    fun format(tokens: List<Token>): String {
        val formattedCode = mutableListOf<Token>()
        for (token in tokens) {
            formattedCode +=
                when (token.type) {
                    TokenType.COLON -> applyColonFormatting(token)
                    TokenType.EQ -> applyEqualsFormatting(token)
                    TokenType.PRINTLN_FUNCTION -> applyPrintlnFormatting(token)
                    TokenType.SEMICOLON -> applySemicolonFormatting(token)
                    TokenType.LET_KEYWORD -> applyLetFormatting(token)
                    TokenType.STRING -> applyStringFormatting(token)
                    else -> token
                }
        }
        return formattedCode.joinToString(separator = "") { it.value }
    }

    private fun applyStringFormatting(token: Token): Token {
        // Asegura que los strings se mantengan entre comillas
        val value =
            if (token.value.startsWith("\"") && token.value.endsWith("\"")) {
                token.value // El string ya está entre comillas, no es necesario hacer nada
            } else {
                "\"${token.value}\"" // Agrega comillas al inicio y al final del string si no están presentes
            }
        return Token(TokenType.STRING, value, token.positionStart, token.positionEnd)
    }

    private fun applyLetFormatting(token: Token): Token {
        // Asegurar que haya un espacio después de "let"
        val spaceAfterLet = if (formatRules.spaceAfterLet) " " else ""

        return Token(TokenType.LET_KEYWORD, "let$spaceAfterLet", token.positionStart, token.positionEnd)
    }

    private fun applySemicolonFormatting(token: Token): Token {
        // Asegurar que haya un salto de línea después del punto y coma
        return Token(TokenType.SEMICOLON, ";\n", token.positionStart, token.positionEnd)
    }

    private fun applyPrintlnFormatting(token: Token): Token {
        // Verificar si se debe agregar salto de línea antes del println
        val newlineBefore = "\n".repeat(formatRules.newlineBeforePrintln)

        return Token(TokenType.PRINTLN_FUNCTION, "${newlineBefore}println", token.positionStart, token.positionEnd)
    }

    private fun applyEqualsFormatting(token: Token): Token {
        // Verificar si se debe agregar espacio antes y/o después del igual
        val spaceBefore = if (formatRules.spaceAroundAssignment) " " else ""
        val spaceAfter = if (formatRules.spaceAroundAssignment) " " else ""

        return Token(TokenType.EQ, "$spaceBefore=$spaceAfter", token.positionStart, token.positionEnd)
    }

    private fun applyColonFormatting(token: Token): Token {
        // Verificar si se debe agregar espacio antes y/o después del :
        val spaceBefore = if (formatRules.spaceBeforeColon) " " else ""
        val spaceAfter = if (formatRules.spaceAfterColon) " " else ""

        return Token(TokenType.COLON, "$spaceBefore:$spaceAfter", token.positionStart, token.positionEnd)
    }
}
