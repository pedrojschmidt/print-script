class Lexer_1 (
    private val input: String,
    private var position: Int = 0,
    private var positionX: Int = 1,
    private var positionY: Int = 1,
    private var tokenList: List<Token_1> = listOf(),
    ) {
        fun makeTokens(): List<Token_1> {
            while (position < input.length) {
                val currentChar = input[position]
                when {
                    currentChar.isDigit() -> {
                        tokenList += makeNumber()
                    }
                    currentChar.isLetter() -> {
                        tokenList += makeIdentifier()
                    }
                    currentChar == '(' -> {
                        tokenList += Token_1(TokenType_1.LPAREN, "(", Position(positionX, positionY), Position(positionX + 1, positionY))
                        position++
                        positionX++
                    }
                    currentChar == ')' -> {
                        tokenList += Token_1(TokenType_1.RPAREN, ")", Position(positionX, positionY), Position(positionX + 1, positionY))
                        position++
                        positionX++
                    }
                    currentChar == '{' -> {
                        tokenList += Token_1(TokenType_1.LKEY, "{", Position(positionX, positionY), Position(positionX + 1, positionY))
                        position++
                        positionX++
                    }
                    currentChar == '}' -> {
                        tokenList += Token_1(TokenType_1.RKEY, "}", Position(positionX, positionY), Position(positionX + 1, positionY))
                        position++
                        positionX++
                    }
                    currentChar == '=' -> {
                        tokenList += Token_1(TokenType_1.EQ, "=", Position(positionX, positionY), Position(positionX + 1, positionY))
                        position++
                        positionX++
                    }
                    currentChar == ':' -> {
                        tokenList += Token_1(TokenType_1.COLON, ":", Position(positionX, positionY), Position(positionX + 1, positionY))
                        position++
                        positionX++
                    }
                    currentChar == '\"' || currentChar == '\'' -> {
                        tokenList += makeString()
                    }
                    currentChar == '+' -> {
                        tokenList += Token_1(TokenType_1.PLUS, "+", Position(positionX, positionY), Position(positionX + 1, positionY))
                        position++
                        positionX++
                    }
                    currentChar == '-' -> {
                        tokenList += Token_1(TokenType_1.MINUS, "-", Position(positionX, positionY), Position(positionX + 1, positionY))
                        position++
                        positionX++
                    }
                    currentChar == '*' -> {
                        tokenList += Token_1(TokenType_1.TIMES, "*", Position(positionX, positionY), Position(positionX + 1, positionY))
                        position++
                        positionX++
                    }
                    currentChar == '/' -> {
                        tokenList += Token_1(TokenType_1.DIV, "/", Position(positionX, positionY), Position(positionX + 1, positionY))
                        position++
                        positionX++
                    }
                    currentChar == ' ' -> {
                        position++
                        positionX++
                    }
                    // Para interpretar un salto de linea estamos asumiendo que se hace con \n
                    // Pero puede ser que sea con ;
                    currentChar == ';' -> {
                        tokenList += Token_1(TokenType_1.SEMICOLON, ";", Position(positionX, positionY), Position(positionX + 1, positionY))
                        position++
                        positionX = 1
                        positionY++
                    }
                    else -> {
                        throw Exception("Error: Caracter no reconocido")
                    }
                }
            }
            return tokenList
        }

        private fun makeIdentifier(): Token_1 {
            var identifier = ""
            // Mientras sea una letra o un digito, lo agregamos al identificador
            while (position < input.length && (input[position].isLetter() || input[position].isDigit())) {
                identifier += input[position]
                position++
                positionX++
            }
            // Si el identificador es una palabra reservada, devolvemos el token correspondiente
            return when (identifier) {
                "let" -> Token_1(TokenType_1.LET_KEYWORD, "let", Position(positionX - 3, positionY), Position(positionX, positionY))
                "const" -> Token_1(TokenType_1.CONST_KEYWORD, "const", Position(positionX - 5, positionY), Position(positionX, positionY))
                "println" -> Token_1(TokenType_1.PRINTLN_FUNCTION, "println", Position(positionX - 7, positionY), Position(positionX, positionY))
                "if" -> Token_1(TokenType_1.IF_FUNCTION, "if", Position(positionX - 2, positionY), Position(positionX, positionY))
                "else" -> Token_1(TokenType_1.ELSE, "else", Position(positionX - 4, positionY), Position(positionX, positionY))
                "number" -> Token_1(TokenType_1.NUMBER_TYPE, "number", Position(positionX - 6, positionY), Position(positionX, positionY))
                "string" -> Token_1(TokenType_1.STRING_TYPE, "string", Position(positionX - 6, positionY), Position(positionX, positionY))
                "boolean" -> Token_1(TokenType_1.BOOLEAN_TYPE, "boolean", Position(positionX - 7, positionY), Position(positionX, positionY))
                "true" -> Token_1(TokenType_1.BOOLEAN, "true", Position(positionX - 4, positionY), Position(positionX, positionY))
                "false" -> Token_1(TokenType_1.BOOLEAN, "false", Position(positionX - 5, positionY), Position(positionX, positionY))
                "readInput" -> Token_1(TokenType_1.READINPUT_FUNCTION, "readInput", Position(positionX - 9, positionY), Position(positionX, positionY))
                "readEnv" -> Token_1(TokenType_1.READENV_FUNCTION, "readEnv", Position(positionX - 7, positionY), Position(positionX, positionY))
                else ->
                    Token_1(
                        TokenType_1.IDENTIFIER,
                        identifier,
                        Position(positionX - identifier.length, positionY),
                        Position(positionX, positionY),
                    )
            }
            // falta resolver como diferenciar entre STRING_TYPE y STRING (Debe ser manejando las comillas "")
        }

        // Lo hacemos caracter por caracter porque no sabemos cuantos digitos tiene el numero
        private fun makeNumber(): Token_1 {
            var number = ""
            while (position < input.length && input[position].isDigit()) {
                number += input[position]
                position++
                positionX++
            }
            return Token_1(TokenType_1.NUMBER, number, Position(positionX - number.length, positionY), Position(positionX, positionY))
        }

        private fun makeString(): Token_1 {
            var string = ""
            // Identifica el tipo de comillas que está usando
            val quoteType = input[position]
            // Avanza la posición para no incluir las comillas en el string
            position++
            positionX++
            while (position < input.length && input[position] != quoteType) {
                string += input[position]
                position++
                positionX++
            }
            // Avanza la posición para no incluir las comillas en el string
            position++
            positionX++
            return Token_1(TokenType_1.STRING, string, Position(positionX - string.length - 2, positionY), Position(positionX, positionY))
        }
}

