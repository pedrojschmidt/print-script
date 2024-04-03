class StaticCodeAnalyzer {
    fun analyze(tokens: List<Token>): List<StaticCodeIssue> {
        val issues = mutableListOf<StaticCodeIssue>()
        var lineIndex = 1
        var columnIndex = 1
        for (token in tokens) {
            if (token.type == TokenType.SEMICOLON) {
                lineIndex++
            }
            if (token.type == TokenType.STRING_TYPE && tokens[5].type != TokenType.STRING) {
                issues.add(StaticCodeIssue("La declaración de variable no coincide con el tipo del valor asignado", Position(lineIndex, columnIndex)))
            } else if (token.type == TokenType.NUMBER_TYPE && tokens[5].type != TokenType.NUMBER) {
                issues.add(StaticCodeIssue("La declaración de variable no coincide con el tipo del valor asignado", Position(lineIndex, columnIndex)))
            }
            if (token.type == TokenType.PRINTLN_FUNCTION) {
                val argument = extractPrintlnArgument(tokens, lineIndex)
                if (!isValidPrintlnArgument(argument)) {
                    issues.add(StaticCodeIssue("La función println debe llamarse solo con un identificador o un literal: $argument", Position(lineIndex, columnIndex)))
                }
            }
            columnIndex += token.value.length
        }
        return issues
    }

    private fun extractPrintlnArgument(
        tokens: List<Token>,
        startIndex: Int,
    ): List<Token> {
        var index = startIndex
        val argumentTokens = mutableListOf<Token>()
        // Buscar la llamada a la función println
        while (index < tokens.size - 1) {
            if (tokens[index].type == TokenType.LPAREN) {
                // Encontrar el argumento dentro de los paréntesis
                var parenthesesCount = 1
                var currentIndex = index + 1
                while (parenthesesCount > 0 && currentIndex < tokens.size) {
                    val token = tokens[currentIndex]
                    if (token.value == "(") {
                        parenthesesCount++
                    } else if (token.value == ")") {
                        parenthesesCount--
                    }
                    argumentTokens.add(token)
                    currentIndex++
                }
            }
            index++
        }
        return argumentTokens
    }

    private fun isValidPrintlnArgument(printlnTokens: List<Token>): Boolean {
        // val expressionTokens = printlnTokens.subList(2, printlnTokens.size - 1)
        val operators = setOf(TokenType.PLUS, TokenType.MINUS, TokenType.TIMES, TokenType.DIV)

        // Verificar si la expresión contiene operadores
        for (token in printlnTokens) {
            if (token.type in operators) {
                return false
            }
        }

        // Verificar si la expresión contiene un identificador o un literal
        for (token in printlnTokens) {
            if (token.type == TokenType.IDENTIFIER || token.type == TokenType.STRING || token.type == TokenType.NUMBER) {
                return true
            }
        }

        return false
    }
}
