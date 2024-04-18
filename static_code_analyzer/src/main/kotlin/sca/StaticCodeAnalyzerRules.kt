package sca

data class StaticCodeAnalyzerRules(
    val printlnArgumentCheck: Boolean,
    val typeMatchingCheck: Boolean,
    val identifierNamingCheck: Boolean,
)
