import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class InterpreterTest{
    @Test
    fun `interpret with declaring and assigning a string`() {
        val lexer = Lexer("let x: string = 'Hello';");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        interpreter.consume(parser.generateAST())
    }
}