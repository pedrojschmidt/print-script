package formatter.rules

class SpaceBeforeColon : FormatterRules<Boolean> {
    private val ruleName = "spaceBeforeColon"
    private val spaceBeforeColon = getConfigFileValue(ruleName, String::toBoolean)

    override fun applyRule() = spaceBeforeColon
}
