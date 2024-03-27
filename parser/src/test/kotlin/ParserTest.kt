import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ParserTest {

    @Test
    fun `parser with declaring and assigning a string`() {
        val lexer = Lexer("let x: string = 'Hello';");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(DeclarationAssignation(Declaration("x","string"),StringOperator("Hello")), ASTList[0])
    }
    @Test
    fun `parser with declaring and assigning a number`() {
        val lexer = Lexer("let x: number = 2;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(DeclarationAssignation(Declaration("x","number"),NumberOperator(2.0)), ASTList[0])
    }
    @Test
    fun `parser with declaring and assigning an operation`() {
        val lexer = Lexer("let x: number = 1+3-1*2/2;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(DeclarationAssignation(Declaration("x","number"),
            BinaryOperation(NumberOperator(1.0),"+",
                BinaryOperation(NumberOperator(3.0),"-",
                    BinaryOperation(NumberOperator(1.0),"*",
                        BinaryOperation(NumberOperator(2.0),"/",NumberOperator(2.0))))))
                , ASTList[0]);
    }
    @Test
    fun `parser with declaring and assigning an identifier`() {
        val lexer = Lexer("let x: string = y;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(DeclarationAssignation(Declaration("x","string"),IdentifierOperator("y")), ASTList[0])
    }
    @Test
    fun `parser with declaring and assigning a sum of strings`() {
        val lexer = Lexer("let x: string = 'Hello' + 'World';");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(DeclarationAssignation(Declaration("x","string"),
            BinaryOperation(StringOperator("Hello"),"+",StringOperator("World"))), ASTList[0])
    }
    @Test
    fun `parser with declaring and assigning a sum of string and number`() {
        val lexer = Lexer("let x: string = 'Hello' + 1;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(DeclarationAssignation(Declaration("x","string"),
            BinaryOperation(StringOperator("Hello"),"+",NumberOperator(1.0))), ASTList[0])
    }

    @Test
    fun `parser with declaring and assigning a sum of identifiers`() {
        val lexer = Lexer("let x: string = y + z;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(DeclarationAssignation(Declaration("x","string"),
            BinaryOperation(IdentifierOperator("y"),"+",IdentifierOperator("z"))), ASTList[0])
    }

    @Test
    fun `parser with declaring a string`() {
        val lexer = Lexer("let x: string;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(Declaration("x","string"), ASTList[0])
    }

    @Test
    fun `parser with declaring a number`() {
        val lexer = Lexer("let x: number;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(Declaration("x","number"), ASTList[0])
    }

    /*@Test
    fun `parser with assigning a number`() {
        val lexer = Lexer("x = 1;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(SimpleAssignation("x", NumberOperator(1.0)), ASTList[0])
    }
    @Test
    fun `parser with assigning a string`() {
        val lexer = Lexer("x = 'Hello';");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(SimpleAssignation("x", StringOperator("Hello")), ASTList[0])
    }
    @Test
    fun `parser with assigning an identifier`() {
        val lexer = Lexer("x = y;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(SimpleAssignation("x", IdentifierOperator("y")), ASTList[0])
    }
    @Test
    fun `parser with assigning a sum of numbers`() {
        val lexer = Lexer("x = 1+3-1*2/2;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(SimpleAssignation("x", BinaryOperation(NumberOperator(1.0),"+",
            BinaryOperation(NumberOperator(3.0),"-",
                BinaryOperation(NumberOperator(1.0),"*",
                    BinaryOperation(NumberOperator(2.0),"/",NumberOperator(2.0)))))), ASTList[0])
    }
    @Test
    fun `parser with assigning a sum of strings`() {
        val lexer = Lexer("x = 'Hello' + 'World';");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(SimpleAssignation("x", BinaryOperation(StringOperator("Hello"),"+",StringOperator("World"))),
            ASTList[0])
    }
    @Test
    fun `parser with assigning a sum of a string and a number`() {
        val lexer = Lexer("x = 'Hello' + 1;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(SimpleAssignation("x", BinaryOperation(StringOperator("Hello"),"+",NumberOperator(1.0))),
            ASTList[0])
    }
    @Test
    fun `parser with assigning a sum of identifiers`() {
        val lexer = Lexer("x = y + z;");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(SimpleAssignation("x", BinaryOperation(IdentifierOperator("y"),"+",IdentifierOperator("z"))),
            ASTList[0])
    }

     */

    @Test
    fun `parser with printing a string`() {
        val lexer = Lexer("println('Hello');");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(Method("println", StringOperator("Hello")),
            ASTList[0])
    }
    @Test
    fun `parser with printing a number`() {
        val lexer = Lexer("println(1);");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(Method("println", NumberOperator(1.0)),
            ASTList[0])
    }
    @Test
    fun `parser with printing an identifier`() {
        val lexer = Lexer("println(x);");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(Method("println", IdentifierOperator("x")),
            ASTList[0])
    }
    @Test
    fun `parser with printing a sum of identifiers`() {
        val lexer = Lexer("println(x+y);");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(Method("println", BinaryOperation(IdentifierOperator("x"),"+",IdentifierOperator("y"))),
            ASTList[0])
    }

    @Test
    fun `parser with printing a sum of strings`() {
        val lexer = Lexer("println('Hello'+'World');");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(Method("println", BinaryOperation(StringOperator("Hello"),"+",StringOperator("World"))),
            ASTList[0])
    }
    @Test
    fun `parser with printing a sum of numbers`() {
        val lexer = Lexer("println(1+2);");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(Method("println", BinaryOperation(NumberOperator(1.0),"+",NumberOperator(2.0))),
            ASTList[0])
    }
    @Test
    fun `parser with printing a sum of a string and a number`() {
        val lexer = Lexer("println('Hello'+1);");
        val parser = Parser(lexer.makeTokens());
        val ASTList: List<ASTNode> = parser.generateAST();

        assertEquals(Method("println", BinaryOperation(StringOperator("Hello"),"+",NumberOperator(1.0))),
            ASTList[0])
    }
}