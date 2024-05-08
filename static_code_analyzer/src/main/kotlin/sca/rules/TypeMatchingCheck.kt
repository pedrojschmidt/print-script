package sca.rules

class TypeMatchingCheck : AnalyzerRules {
    override fun applyRule(): Boolean {
        return getConfigFileValue("typeMatchingCheck") { it.toBoolean() }
    }
}
