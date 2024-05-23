package sca

import token.Position

data class StaticCodeIssue(
    val message: String,
    val position: Position,
)
