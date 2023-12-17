package utils

import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * Grid helper class, from
 * https://todd.ginsberg.com/post/advent-of-code/2021/
 */
data class Point2d(var x: Long, var y: Long) {

    constructor(x: Int, y: Int) : this(x.toLong(), y.toLong())

    infix fun sharesAxisWith(that: Point2d): Boolean =
        x == that.x || y == that.y

    infix fun lineTo(that: Point2d): List<Point2d> {
        val xDelta = (that.x - x).sign
        val yDelta = (that.y - y).sign
        val steps = maxOf((x - that.x).absoluteValue, (y - that.y).absoluteValue)
        return (1..steps).scan(this) { last, _ -> Point2d(last.x + xDelta, last.y + yDelta) }
    }

    fun chebyshevDistance(that: Point2d): Long {
        return maxOf((x - that.x).absoluteValue, (y - that.y).absoluteValue)
    }

    fun manhattanDistance(that: Point2d): Long {
        return (abs(this.x - that.x) + abs(this.y - that.y))
    }


    fun cardinalNeighbors(): Set<Point2d> =
        setOf(
            Point2d(x, y + 1),
            Point2d(x, y - 1),
            Point2d(x + 1, y),
            Point2d(x - 1, y)
        )

    fun neighbors(): Set<Point2d> =
        cardinalNeighbors() + setOf(
            Point2d(x - 1, y - 1),
            Point2d(x - 1, y + 1),
            Point2d(x + 1, y - 1),
            Point2d(x + 1, y + 1)
        )

    fun times(times: Int): List<Point2d> {
        return (1..times).map { this }
    }

    operator fun plus(other: Point2d): Point2d =
        Point2d(this.x + other.x, this.y + other.y)

    operator fun minus(other: Point2d): Point2d =
        Point2d(this.x - other.x, this.y - other.y)

    fun distanceTo(other: Point2d): Long =
        (x - other.x).absoluteValue + (y - other.y).absoluteValue

    fun add(other: Pair<Long, Long>): Point2d =
        Point2d(this.x + other.first, this.y + other.second)

    fun compareTo(that: Point2d): Int {
        return when {
            y < that.y -> -1
            y > that.y -> 1
            x < that.x -> -1
            x > that.x -> 1
            else -> 0
        }
    }

    companion object {
        fun of(input: String): Point2d =
            input.split(",").let { (x, y) -> Point2d(x.toLong(), y.toLong()) }
    }
}