package utils

/** Represents a direction to move in. */
sealed interface Direction {

    /** A horizontal direction, either left or right. */
    sealed interface Horizontal : Direction

    /** A vertical direction, either up or down. */
    sealed interface Vertical : Direction

    /** An upward direction. */
    object Up : Vertical

    /** A downward direction. */
    object Down : Vertical

    /** A leftward direction. */
    object Left : Horizontal

    /** A rightward direction. */
    object Right : Horizontal
}

/** Returns the direction opposite this one. */
val Direction.opposite: Direction
    get() = when(this) {
        Direction.Left -> Direction.Right
        Direction.Right -> Direction.Left
        Direction.Down -> Direction.Up
        Direction.Up -> Direction.Down
    }

/** Returns the point one unit distance away from this one in a given [direction]. */
fun Point2d.move(direction: Direction): Point2d = when(direction) {
    Direction.Left -> Point2d(x - 1, y)
    Direction.Right -> Point2d(x + 1, y)
    Direction.Down -> Point2d(x, y - 1)
    Direction.Up -> Point2d(x, y + 1)
}