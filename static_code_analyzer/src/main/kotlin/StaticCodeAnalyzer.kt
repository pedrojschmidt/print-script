class StaticCodeAnalyzer {
    fun analyze(code: String): List<StaticCodeIssue> {
        val issues = mutableListOf<StaticCodeIssue>()
        val lines = code.split("\n")
        var lineIndex = 1
        for (line in lines) {
            var columnIndex = 1
            val lexer = Lexer(line)
            val tokens = lexer.makeTokens()
            for (token in tokens) {
                if (token.type == TokenType.STRING_TYPE && tokens[5].type != TokenType.STRING) {
                    issues.add(StaticCodeIssue("La declaración de variable no coincide con el tipo del valor asignado", Position(lineIndex, columnIndex)))

                }else if (token.type == TokenType.NUMBER_TYPE && tokens[5].type != TokenType.NUMBER) {
                    issues.add(StaticCodeIssue("La declaración de variable no coincide con el tipo del valor asignado", Position(lineIndex, columnIndex)))
                }
                if (token.type == TokenType.PRINTLN_FUNCTION) {
                    val argument = extractPrintlnArgument(tokens, columnIndex)
                    if (!isValidPrintlnArgument(argument)) {
                        issues.add(StaticCodeIssue("La función println debe llamarse solo con un identificador o un literal: $argument", Position(lineIndex, columnIndex)))
                    }
                }
                columnIndex += token.value.length
            }
            lineIndex++
        }
        return issues
    }

    private fun extractPrintlnArgument(tokens: List<Token>, startIndex: Int): List<Token> {
        var index = startIndex
        // Buscar la llamada a la función println
        while (index < tokens.size - 1) {
            if (tokens[index].type == TokenType.LPAREN) {
                // Encontrar el argumento dentro de los paréntesis
                val argumentTokens = mutableListOf<Token>()
                var parenthesesCount = 1
                var currentIndex = index + 2
                while (parenthesesCount > 0 && currentIndex < tokens.size) {
                    val token = tokens[currentIndex]
                    if (token.value == "(") {
                        parenthesesCount++
                    } else if (token.value == ")") {
                        parenthesesCount--
                    }
                    if (parenthesesCount > 0) {
                        argumentTokens.add(token)
                    }
                    currentIndex++
                }
            }
            index++
        }
        return tokens
    }

    private fun isValidPrintlnArgument(printlnTokens: List<Token>): Boolean {
        val expressionTokens = printlnTokens.subList(2, printlnTokens.size - 1)
        val operators = setOf(TokenType.PLUS, TokenType.MINUS, TokenType.TIMES, TokenType.DIV)

        // Verificar si la expresión contiene operadores
        for (token in expressionTokens) {
            if (token.type in operators) {
                return false
            }
        }

        // Verificar si la expresión contiene un identificador o un literal
        for (token in expressionTokens) {
            if (token.type == TokenType.IDENTIFIER || token.type == TokenType.STRING || token.type == TokenType.NUMBER) {
                return true
            }
        }

        return false
    }
}
