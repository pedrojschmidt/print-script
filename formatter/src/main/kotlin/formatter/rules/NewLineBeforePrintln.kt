package formatter.rules

class NewLineBeforePrintln : FormattingRules<Int> {
    private val ruleName = "newlineBeforePrintln"
    private val newlineBeforePrintln = getConfigFileValue(ruleName, String::toInt)

    override fun applyRule() = newlineBeforePrintln
}
