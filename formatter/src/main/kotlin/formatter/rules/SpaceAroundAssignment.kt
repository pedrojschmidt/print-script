package formatter.rules

class SpaceAroundAssignment(
    configFilePath: String,
) : FormattingRules<Boolean> {
    private val ruleName = "spaceAroundAssignment"
    private val spaceAroundAssignment = getConfigFileValue(ruleName, String::toBoolean, configFilePath)

    override fun applyRule() = spaceAroundAssignment
}
