package version1

interface TokenMaker {
    fun makeToken(
        input: String,
        position: Int,
        positionX: Int,
        positionY: Int,
    ): Token?
}
