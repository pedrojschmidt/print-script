package formatter.rules

class NSpacesIndentationForIfStatement : FormattingRules<Int> {
    private val ruleName = "nSpacesIndentationForIfStatement"
    private val nSpacesIndentationForIfStatement = getConfigFileValue(ruleName, String::toInt)

    override fun applyRule() = nSpacesIndentationForIfStatement
}
