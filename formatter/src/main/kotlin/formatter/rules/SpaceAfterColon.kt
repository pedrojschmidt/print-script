package formatter.rules

class SpaceAfterColon : FormatterRules<Boolean> {
    private val ruleName = "spaceAfterColon"
    private val spaceAfterColon = getConfigFileValue(ruleName, String::toBoolean)

    override fun applyRule() = spaceAfterColon
}
