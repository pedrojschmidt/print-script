import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class InterpreterTest{
    @Test
    fun `interpret with declaring and assigning a string`() {
        val lexer = Lexer("let x: string = 'Hello';");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        interpreter.consume(parser.generateAST())

        assertEquals("Hello", interpreter.getMap()[Variable("x","string")]);
    }

    @Test
    fun `interpret with declaring and assigning a number`() {
        val lexer = Lexer("let x: number = 20;");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        interpreter.consume(parser.generateAST())

        assertEquals("20.0", interpreter.getMap()[Variable("x","number")]);
    }
    @Test
    fun `interpret with declaring and assigning an identifier`() {
        val lexer = Lexer("let y: string = 'Hello'; " +
                "let x: string = y;");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        interpreter.consume(parser.generateAST())

        assertEquals("Hello", interpreter.getMap()[Variable("x","string")]);
    }
    @Test
    fun `interpret with declaring and assigning a sum of strings`() {
        val lexer = Lexer("let x: string = 'Hello' + 'World';");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        interpreter.consume(parser.generateAST())

        assertEquals("HelloWorld", interpreter.getMap()[Variable("x","string")]);
    }
    @Test
    fun `interpret with declaring and assigning a sum`() {
        val lexer = Lexer("let x: number = 2+3-1*2/2;");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        interpreter.consume(parser.generateAST())

        assertEquals("4.0", interpreter.getMap()[Variable("x","number")]);
    }
    @Test
    fun `interpret with declaring and assigning a sum of number & string`() {
        val lexer = Lexer("let x: string = 'Hello' + 1;");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        interpreter.consume(parser.generateAST())

        assertEquals("Hello1.0", interpreter.getMap()[Variable("x","string")]);
    }
    @Test
    fun `interpret with printing a string`() {
        val lexer = Lexer("println('Hello');");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        val result = interpreter.consume(parser.generateAST());

        assertEquals("Hello\n", result);
    }

    @Test
    fun `interpret with printing a number`() {
        val lexer = Lexer("println(20);");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        val result = interpreter.consume(parser.generateAST());

        assertEquals("20.0\n", result);
    }
    @Test
    fun `interpret with printing an identifier`() {
        val lexer = Lexer("let x: string = 'Hello'; " +
                "println(x);");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        val result = interpreter.consume(parser.generateAST());

        assertEquals("Hello\n", result);
    }
    @Test
    fun `interpret with printing a sum of strings`() {
        val lexer = Lexer("println('Hello' + 'World');");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        val result = interpreter.consume(parser.generateAST());

        assertEquals("HelloWorld\n", result);
    }
    @Test
    fun `interpret with printing a sum`() {
        val lexer = Lexer("println(2+3-1*2/2);");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        val result = interpreter.consume(parser.generateAST());

        assertEquals("4.0\n", result);
    }

    @Test
    fun `interpret with printing a sum of number & string`() {
        val lexer = Lexer("println('Hello' + 1);");
        val parser = Parser(lexer.makeTokens());
        val interpreter = Interpreter();

        val result = interpreter.consume(parser.generateAST());

        assertEquals("Hello1.0\n", result);
    }
}