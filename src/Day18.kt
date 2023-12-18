import utils.measureTimeMillisPrint
import utils.readInputString
import kotlin.math.absoluteValue

typealias Point = Pair<Int, Int>

enum class Dir(val point: Point) {
    East(Point(1, 0)), South(Point(0, 1)), West(Point(-1, 0)), North(Point(0, -1))
}

class Day18(input: String) {

    operator fun Point.plus(other: Pair<Int, Int>): Point {
        return Pair(this.first + other.first, this.second + other.second)
    }

    operator fun Pair<Int, Int>.times(other: Int): Point {
        return Pair(this.first * other, this.second * other)
    }

    private val parse = input.lineSequence().map { line ->
        line.split(" ").let { (direction, distance, color) ->
            val dir = when (direction[0]) {
                'R' -> Dir.East
                'D' -> Dir.South
                'L' -> Dir.West
                'U' -> Dir.North
                else -> error("Unknown Direction $direction")
            }
            Triple(dir, distance.toInt(), color.substring(2..7).toInt(16))
        }
    }

    private fun Sequence<Pair<Dir, Int>>.solve() =
        runningFold(0 to 0) { point, (dir, amount) -> point + dir.point * amount }.zipWithNext { (y1, x1), (_, x2) -> (x2 - x1) * y1.toLong() }
            .sum().absoluteValue + sumOf { it.second } / 2 + 1

    fun part1() = parse.map { (a, b, _) -> a to b }.solve()
    fun part2() = parse.map { (_, _, c) -> listOf(Dir.East, Dir.South, Dir.West, Dir.North)[c % 16] to c / 16 }.solve()
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputString("Day18_test")
    check(Day18(testInput).part1() == 62L)
    measureTimeMillisPrint {
        val input = readInputString("Day18")

        println(Day18(input).part1())
        println(Day18(input).part2())
    }
}
