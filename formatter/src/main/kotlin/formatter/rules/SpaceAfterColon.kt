package formatter.rules

class SpaceAfterColon(
    configFilePath: String,
) : FormattingRules<Boolean> {
    private val ruleName = "spaceAfterColon"
    private val spaceAfterColon = getConfigFileValue(ruleName, String::toBoolean, configFilePath)

    override fun applyRule() = spaceAfterColon
}
