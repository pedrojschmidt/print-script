package sca

import sca.rules.FunctionArgumentCheck
import sca.rules.IdentifierNamingCheck
import sca.rules.TypeMatchingCheck

data class StaticCodeAnalyzerRules(
    val functionArgumentCheck: Boolean = FunctionArgumentCheck().applyRule(),
    val typeMatchingCheck: Boolean = TypeMatchingCheck().applyRule(),
    val identifierNamingCheck: Boolean = IdentifierNamingCheck().applyRule(),
)
