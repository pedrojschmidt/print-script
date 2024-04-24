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

        fun fromDefault(): Formatter {
            val formatRules =
                FormatRules(
                    spaceBeforeColon = true,
                    spaceAfterColon = true,
                    spaceAroundAssignment = true,
                    newlineBeforePrintln = 1,
                    spaceAfterLet = true,
                )
            return Formatter(formatRules)
        }
    }

    fun formatString(ast: List<ASTNode>): String {
        val formattedCode = ast.map { formatNode(it) }
        return formattedCode.joinToString(separator = "")
    }

    private fun formatNode(astNode: ASTNode): String {
        return when (astNode) {
            is Declaration -> applyDeclarationFormatting(astNode)
            is DeclarationAssignation -> applyDeclarationAssignationFormatting(astNode)
            is SimpleAssignation -> applySimpleAssignationFormatting(astNode)
            is Method -> applyMethodFormatting(astNode)
            else -> throw IllegalArgumentException("Unsupported ASTNode type: ${astNode.javaClass.simpleName}")
        }
    }

    private fun applyMethodFormatting(astNode: Method): String {
        val newlineBefore = "\n".repeat(formatRules.newlineBeforePrintln)
        return when (val value = astNode.value) {
            is StringOperator -> "$newlineBefore${formatPrintln(value.value)}"
            is NumberOperator -> "$newlineBefore${formatPrintln(value.value)}"
            is BinaryOperation -> {
                "$newlineBefore${formatPrintln("${formatBinaryOperation(value.left)} ${value.symbol} ${formatBinaryOperation(value.right)}")}"
            }
            else -> "$newlineBefore${formatPrintln((value as IdentifierOperator).identifier)}"
        }
    }

    private fun formatPrintln(value: Any) = "println($value);${applySemicolonFormatting()}"

    private fun applySimpleAssignationFormatting(astNode: SimpleAssignation): String {
        val assignation = astNode.value as BinaryOperation
        val leftOperator = (assignation.left as NumberOperator).value
        val symbol = assignation.symbol
        val rightOperator = (assignation.right as NumberOperator).value

        return "${astNode.identifier}${applyEqualsFormatting()} $leftOperator $symbol $rightOperator;${applySemicolonFormatting()}"
    }

    private fun applyDeclarationAssignationFormatting(astNode: DeclarationAssignation): String {
        val spaceAroundAssignment = if (formatRules.spaceAroundAssignment) " " else ""
        val spaceAfterLet = applyLetFormatting()
        val assignationValue =
            when (val assignation = astNode.value) {
                is StringOperator -> "\"${assignation.value}\""
                is NumberOperator -> "${assignation.value}"
                is BinaryOperation -> formatBinaryOperation(assignation)
                is IdentifierOperator -> assignation.identifier
                else -> throw IllegalArgumentException("Unsupported assignation type: ${assignation.javaClass.simpleName}")
            }

        return "let$spaceAfterLet${astNode.declaration.identifier}${applyColonFormatting()}${astNode.declaration.type}$spaceAroundAssignment=$spaceAroundAssignment$assignationValue;${applySemicolonFormatting()}"
    }

    private fun formatBinaryOperation(binaryNode: BinaryNode): String {
        return when (binaryNode) {
            is NumberOperator -> binaryNode.value.toString()
            is StringOperator -> "\"${binaryNode.value}\""
            is IdentifierOperator -> binaryNode.identifier
            is BinaryOperation -> "${formatBinaryOperation(binaryNode.left)} ${binaryNode.symbol} ${formatBinaryOperation(binaryNode.right)}"
        }
    }

    private fun applyDeclarationFormatting(astNode: Declaration): String {
        return "let${applyLetFormatting()}${astNode.identifier}${applyColonFormatting()}${astNode.type};${applySemicolonFormatting()}"
    }

    private fun applyLetFormatting() = if (formatRules.spaceAfterLet) " " else ""

    private fun applySemicolonFormatting() = "\n"

    private fun applyEqualsFormatting() = formatWithSpacesEqual(formatRules.spaceAroundAssignment)

    private fun applyColonFormatting() = formatWithSpaces(formatRules.spaceBeforeColon, formatRules.spaceAfterColon)

    private fun formatWithSpaces(
        before: Boolean = false,
        after: Boolean = false,
    ) = "${if (before) " " else ""}:${if (after) " " else ""}"

    private fun formatWithSpacesEqual(
        before: Boolean = false,
        after: Boolean = false,
    ) = "${if (before) " " else ""}=${if (after) " " else ""}"
}
