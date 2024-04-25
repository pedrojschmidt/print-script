package formatter.rules

class SpaceAroundAssignment : FormatterRules<Boolean> {
    private val ruleName = "spaceAroundAssignment"
    private val spaceAroundAssignment = getConfigFileValue(ruleName, String::toBoolean)

    override fun applyRule() = spaceAroundAssignment
}
