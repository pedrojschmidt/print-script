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

            val formatRules =
                FormatRules(
                    spaceBeforeColon,
                    spaceAfterColon,
                    spaceAroundAssignment,
                    newlineBeforePrintln,
                    spaceAfterLet,
                )
            return Formatter(formatRules)
        }
    }

    fun format(ast: List<ASTNode>): String {
        val formattedCode = mutableListOf<String>()
        for (astNode in ast) {
            formattedCode +=
                when (astNode) {
                    is Declaration -> applyDeclarationFormatting(astNode)
                    is DeclarationAssignation -> applyDeclarationAssignationFormatting(astNode)
                    is SimpleAssignation -> applySimpleAssignationFormatting(astNode)
                    is Method -> applyMethodFormatting(astNode)
                    else -> throw IllegalArgumentException("Unsupported ASTNode type: ${astNode.javaClass.simpleName}")
                }
        }
        return formattedCode.joinToString(separator = "\n")
    }

    private fun applyMethodFormatting(astNode: Method): String {
        // val newlineBefore = "\n".repeat(formatRules.newlineBeforePrintln)
        return when (astNode.value) {
            is StringOperator -> {
                "println(\"${(astNode.value as StringOperator).value}\");${applySemicolonFormatting()}"
            }

            is NumberOperator -> {
                "println(${(astNode.value as NumberOperator).value});${applySemicolonFormatting()}"
            }

            is BinaryOperation -> {
                val leftOperator = ((astNode.value as BinaryOperation).left as IdentifierOperator).identifier
                val symbol = (astNode.value as BinaryOperation).symbol
                val rightOperator = ((astNode.value as BinaryOperation).right as NumberOperator).value
                "println($leftOperator $symbol $rightOperator);${applySemicolonFormatting()}"
            }

            else -> "println(${(astNode.value as IdentifierOperator).identifier});${applySemicolonFormatting()}"
        }
    }

    private fun applySimpleAssignationFormatting(astNode: SimpleAssignation): String {
        val leftOperator = ((astNode.assignation as BinaryOperation).left as NumberOperator).value
        val symbol = (astNode.assignation as BinaryOperation).symbol
        val rightOperator = ((astNode.assignation as BinaryOperation).right as NumberOperator).value

        return "${astNode.identifier}${applyEqualsFormatting()} $leftOperator $symbol $rightOperator;${applySemicolonFormatting()}"
    }

    private fun applyDeclarationAssignationFormatting(astNode: DeclarationAssignation): String {
        val spaceAroundAssignment = if (formatRules.spaceAroundAssignment) " " else ""
        val spaceAfterLet = applyLetFormatting()
        if (astNode.assignation is StringOperator) {
            return "let$spaceAfterLet${astNode.declaration.identifier}${applyColonFormatting()}${astNode.declaration.type}$spaceAroundAssignment=$spaceAroundAssignment\"${(astNode.assignation as StringOperator).value}\";${applySemicolonFormatting()}"
        } else if (astNode.assignation is NumberOperator) {
            return "let$spaceAfterLet${astNode.declaration.identifier}${applyColonFormatting()}${astNode.declaration.type}$spaceAroundAssignment=$spaceAroundAssignment${(astNode.assignation as NumberOperator).value};${applySemicolonFormatting()}"
        }

        val leftOperator = ((astNode.assignation as BinaryOperation).left as NumberOperator).value
        val symbol = (astNode.assignation as BinaryOperation).symbol
        val rightOperator = ((astNode.assignation as BinaryOperation).right as NumberOperator).value

        return "let$spaceAfterLet${astNode.declaration.identifier}${applyColonFormatting()}${astNode.declaration.type}$spaceAroundAssignment=$spaceAroundAssignment$leftOperator$symbol$rightOperator;${applySemicolonFormatting()}"
    }

    private fun applyDeclarationFormatting(astNode: Declaration): String {
        val spaceBefore = if (formatRules.spaceBeforeColon) " " else ""
        val spaceAfter = if (formatRules.spaceAfterColon) " " else ""
        val spaceAfterLet = applyLetFormatting()

        return "let$spaceAfterLet${astNode.identifier}$spaceBefore:$spaceAfter${astNode.type};${applySemicolonFormatting()}"
    }

    private fun applyLetFormatting(): String {
//      Asegurar que haya un espacio después de "let"
        return if (formatRules.spaceAfterLet) " " else ""
    }

    private fun applySemicolonFormatting(): String {
        // Asegurar que haya un salto de línea después del punto y coma
        return "\n"
    }

    private fun applyEqualsFormatting(): String {
        // Verificar si se debe agregar espacio antes y/o después del igual
        val spaceBefore = if (formatRules.spaceAroundAssignment) " " else ""
        val spaceAfter = if (formatRules.spaceAroundAssignment) " " else ""
        return "$spaceBefore=$spaceAfter"
    }

    private fun applyColonFormatting(): String {
        // Verificar si se debe agregar espacio antes y/o después del :
        val spaceBefore = if (formatRules.spaceBeforeColon) " " else ""
        val spaceAfter = if (formatRules.spaceAfterColon) " " else ""

        return "$spaceBefore:$spaceAfter"
    }
}
