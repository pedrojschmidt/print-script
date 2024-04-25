package formatter.rules

class SpaceAfterColon : FormattingRules<Boolean> {
    private val ruleName = "spaceAfterColon"
    private val spaceAfterColon = getConfigFileValue(ruleName, String::toBoolean)

    override fun applyRule() = spaceAfterColon
}
