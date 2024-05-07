package sca.rules

class FunctionArgumentCheck : AnalyzerRules {
    override fun applyRule(): Boolean {
        return getConfigFileValue("printlnArgumentCheck") { it.toBoolean() }
    }
}
