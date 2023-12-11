import utils.*
import kotlin.math.abs

fun main() {

    fun List<String>.galaxies(): List<Point2d> {
        return this.flatMapIndexed{ y, row ->
            row.mapIndexed{ x, col ->
                if(col == '#') Point2d(x,y) else null
            }.filterNotNull()
        }
    }

    fun List<String>.rows() = this.runningFold(0){ prev, row ->
        if(row.all { it ==  '.'}) prev +1
        else prev
    }.drop(1)

    fun List<String>.cols() = this.map { it.toList() }
        .transpose()
        .runningFold(0) { prev, row ->
            if(row.all { it == '.'}) prev + 1
            else prev
    }.drop(1)

    fun part1(input: List<String>, multiplier: Long= 1L): Long {
        val galaxies = input.galaxies()
        val rows = input.rows()
        val cols = input.cols()

        return galaxies.combinations(2).map { (g1, g2) ->
            val distance: Long = g1.manhattanDistance(g2).toLong()
            val expansion: Long = (abs(rows[g1.y] - rows[g2.y]) + abs(cols[g1.x] - cols[g2.x])).toLong()*multiplier
            distance + expansion
        }.sumOf{it}
    }

    fun part2(input: List<String>): Long {
        return part1(input, 999999)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 374L)
    measureTimeMillisPrint {
        val input = readInput("Day11")

        println(part1(input))
        println(part2(input))
    }
}