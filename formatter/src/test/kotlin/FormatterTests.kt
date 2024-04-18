import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream

class FormatterTests {
    @Test
    fun `test formatString with real file`() {
        // Crear una instancia real de ASTNode
        val astList = fillAstList(File("src/test/resources/test1.txt"))

        // Leer el archivo de configuración
        val configFile = File("src/main/kotlin/format_rules.yaml")
        val yamlContent = configFile.readText()

        // Crear el formateador a partir del contenido del archivo YAML
        val formatter = Formatter.fromYaml(yamlContent)

        // Formatear la lista de nodos AST
        val formattedAst = formatter.formatString(astList)

        // Aquí debes reemplazar "expectedString" con la cadena formateada que esperas obtener
        val expectedString =
            "let a : number = 5*5;\n" +
                "let c : string = \"Hello, world!\";\n" +
                "\n" +
                "println(a);\n" +
                "\n" +
                "println(c);\n"

        assertEquals(expectedString, formattedAst)
    }

    private fun fillAstList(file: File): MutableList<ASTNode> {
        val tokenProvider = TokenProvider(FileInputStream(file))
        val parser = Parser.getDefaultParser()
        val astList = mutableListOf<ASTNode>()
        while (tokenProvider.hasNextStatement()) {
            val tokens = tokenProvider.readStatement()
            val ast = parser.generateAST(tokens)
            // Add the AST to the list if it is not null
            ast?.let { astList.add(it) }
        }
        return astList
    }
}
