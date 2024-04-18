import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.io.FileInputStream

class FormatterTests {
    @Test
    fun `test01 formatString with real file`() {
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
            "let a : number = 5 * 5;\n" +
                "let c : string = \"Hello, world!\";\n" +
                "\n" +
                "println(a);\n" +
                "\n" +
                "println(c);\n"

        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test02 formatString with real file`() {
        // Crear una instancia real de ASTNode
        val astList = fillAstList(File("src/test/resources/test2.txt"))

        // Leer el archivo de configuración
        val configFile = File("src/main/kotlin/format_rules.yaml")
        val yamlContent = configFile.readText()

        // Crear el formateador a partir del contenido del archivo YAML
        val formatter = Formatter.fromYaml(yamlContent)

        // Formatear la lista de nodos AST
        val formattedAst = formatter.formatString(astList)

        // Aquí debes reemplazar "expectedString" con la cadena formateada que esperas obtener
        val expectedString =
            "let a : number = 5 * 5;\n" +
                "let b : number = 10 + a;\n" +
                "let c : string = \"Resultado: \" + b;\n" +
                "\n" +
                "println(a);\n" +
                "\n" +
                "println(b);\n" +
                "\n" +
                "println(c);\n"

        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test03 formatString with real file`() {
        // Crear una instancia real de ASTNode
        val astList = fillAstList(File("src/test/resources/test2.txt"))

        // Leer el archivo de configuración
        val configFile = File("src/main/kotlin/format_rules.yaml")

        // Crear el formateador a partir del contenido del archivo YAML
        val formatter = Formatter.fromDefault()

        // Formatear la lista de nodos AST
        val formattedAst = formatter.formatString(astList)

        // Aquí debes reemplazar "expectedString" con la cadena formateada que esperas obtener
        val expectedString =
            "let a : number = 5 * 5;\n" +
                "let b : number = 10 + a;\n" +
                "let c : string = \"Resultado: \" + b;\n" +
                "\n" +
                "println(a);\n" +
                "\n" +
                "println(b);\n" +
                "\n" +
                "println(c);\n"

        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test04 formatString with real file`() {
        // Crear una instancia real de ASTNode
        val astList = fillAstList(File("src/test/resources/test2.txt"))

        // Leer el archivo de configuración
        val configFile = File("src/test/resources/invalid_format_rules.yaml")
        val yamlContent = configFile.readText()

        assertThrows<IllegalArgumentException> {
            // Crear el formateador a partir del contenido del archivo YAML
            Formatter.fromYaml(yamlContent)
        }
    }

    @Test
    fun `test05 formatString with real file`() {
        // Crear una instancia real de ASTNode
        val astList = fillAstList(File("src/test/resources/test2.txt"))

        // Leer el archivo de configuración
        val configFile = File("src/test/resources/test_rules_1.yaml")
        val yamlContent = configFile.readText()

        // Crear el formateador a partir del contenido del archivo YAML
        val formatter = Formatter.fromYaml(yamlContent)

        // Formatear la lista de nodos AST
        val formattedAst = formatter.formatString(astList)

        // Aquí debes reemplazar "expectedString" con la cadena formateada que esperas obtener
        val expectedString =
            "let a : number = 5 * 5;\n" +
                "let b : number = 10 + a;\n" +
                "let c : string = \"Resultado: \" + b;\n" +
                "println(a);\n" +
                "println(b);\n" +
                "println(c);\n"

        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test06 formatString with real file`() {
        // Crear una instancia real de ASTNode
        val astList = fillAstList(File("src/test/resources/test3.txt"))

        // Leer el archivo de configuración
        val configFile = File("src/main/kotlin/format_rules.yaml")
        val yamlContent = configFile.readText()

        // Crear el formateador a partir del contenido del archivo YAML
        val formatter = Formatter.fromYaml(yamlContent)

        // Formatear la lista de nodos AST
        val formattedAst = formatter.formatString(astList)

        // Aquí debes reemplazar "expectedString" con la cadena formateada que esperas obtener
        val expectedString =
            "let a : number;\n" +
                "a = 10 + 5;\n"

        assertEquals(expectedString, formattedAst)
    }

    @Test
    fun `test07 formatString with real file`() {
        // Crear una instancia real de ASTNode
        val astList = fillAstList(File("src/test/resources/test4.txt"))

        // Leer el archivo de configuración
        val configFile = File("src/main/kotlin/format_rules.yaml")
        val yamlContent = configFile.readText()

        // Crear el formateador a partir del contenido del archivo YAML
        val formatter = Formatter.fromYaml(yamlContent)

        // Formatear la lista de nodos AST
        val formattedAst = formatter.formatString(astList)

        // Aquí debes reemplazar "expectedString" con la cadena formateada que esperas obtener
        val expectedString =
            "let a : number = 5 + 5 + 5;\n" +
                "let b : string = \"Hello,\" + \" World\";\n" +
                "let c : string = a;\n" +
                "\n" +
                "println(a + 5);\n" +
                "\n" +
                "println(b + \"!\");\n" +
                "\n" +
                "println(\"Hello, World!\" + \"5\");\n" +
                "\n" +
                "println(\"Hello, World!\" + \"!\");\n" +
                "\n" +
                "println(5 + 5 + 5);\n" +
                "\n" +
                "println(\"a\" + a);\n"

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
