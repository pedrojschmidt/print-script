package formatter.rules

class NewLineBeforePrintln(
    configFilePath: String,
) : FormattingRules<Int> {
    private val ruleName = "newlineBeforePrintln"
    private val newlineBeforePrintln = getConfigFileValue(ruleName, String::toInt, configFilePath)

    override fun applyRule() = newlineBeforePrintln
}
