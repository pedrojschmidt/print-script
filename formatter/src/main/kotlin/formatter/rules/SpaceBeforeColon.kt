package formatter.rules

class SpaceBeforeColon(
    configFilePath: String,
) : FormattingRules<Boolean> {
    private val ruleName = "spaceBeforeColon"
    private val spaceBeforeColon = getConfigFileValue(ruleName, String::toBoolean, configFilePath)

    override fun applyRule() = spaceBeforeColon
}
