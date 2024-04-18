package version1

import Token1

interface TokenMaker1 {
    fun makeToken(
        input: String,
        position: Int,
        positionX: Int,
        positionY: Int,
    ): Token1?
}
