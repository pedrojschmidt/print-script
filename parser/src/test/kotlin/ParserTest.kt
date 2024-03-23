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
        val lexer = Lexer("let x: number = (1+3-1*2/2);");
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
}