package formatter

import formatter.rules.NewLineBeforePrintln
import formatter.rules.SpaceAfterColon
import formatter.rules.SpaceAroundAssignment
import formatter.rules.SpaceBeforeColon

data class FormatRules(
    val spaceBeforeColon: Boolean = SpaceBeforeColon().applyRule(),
    val spaceAfterColon: Boolean = SpaceAfterColon().applyRule(),
    val spaceAroundAssignment: Boolean = SpaceAroundAssignment().applyRule(),
    val newlineBeforePrintln: Int = NewLineBeforePrintln().applyRule(),
)
