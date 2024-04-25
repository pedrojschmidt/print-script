package formatter.rules

class NewLineBeforePrintln : FormatterRules<Int> {
    private val ruleName = "newlineBeforePrintln"
    private val newlineBeforePrintln = getConfigFileValue(ruleName, String::toInt)

    override fun applyRule() = newlineBeforePrintln
}
