package sca.rules

class IdentifierNamingCheck : AnalyzerRules {
    override fun applyRule(): Boolean {
        return getConfigFileValue("identifierNamingCheck") { it.toBoolean() }
    }
}
