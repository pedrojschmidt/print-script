package formatter.rules

class NSpacesIndentationForIfStatement : FormatterRules<Int> {
    private val ruleName = "nSpacesIndentationForIfStatement"
    private val nSpacesIndentationForIfStatement = getConfigFileValue(ruleName, String::toInt)

    override fun applyRule() = nSpacesIndentationForIfStatement
}
