package formatter.rules

class NSpacesIndentationForIfStatement(
    configFilePath: String,
) : FormattingRules<Int> {
    private val ruleName = "nSpacesIndentationForIfStatement"
    private val nSpacesIndentationForIfStatement = getConfigFileValue(ruleName, String::toInt, configFilePath)

    override fun applyRule() = nSpacesIndentationForIfStatement
}
