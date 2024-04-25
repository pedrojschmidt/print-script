package formatter

import formatter.rules.NSpacesIndentationForIfStatement
import formatter.rules.NewLineBeforePrintln
import formatter.rules.SpaceAfterColon
import formatter.rules.SpaceAroundAssignment
import formatter.rules.SpaceBeforeColon

data class FormatRules(
    val configFilePath: String,
    val spaceBeforeColon: Boolean = SpaceBeforeColon(configFilePath).applyRule(),
    val spaceAfterColon: Boolean = SpaceAfterColon(configFilePath).applyRule(),
    val spaceAroundAssignment: Boolean = SpaceAroundAssignment(configFilePath).applyRule(),
    val newlineBeforePrintln: Int = NewLineBeforePrintln(configFilePath).applyRule(),
    val nSpacesIndentationForIfStatement: Int = NSpacesIndentationForIfStatement(configFilePath).applyRule(),
)
