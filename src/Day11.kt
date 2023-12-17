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

    fun List<String>.rowCols() = Pair(this.runningFold(0){ prev:Long, row ->
        if(row.all { it ==  '.'}) prev +1
        else prev
    }, this.map { it.toList() }
        .transpose()
        .runningFold(0) { prev:Long, row ->
            if(row.all { it == '.'}) prev + 1
            else prev
        })

    fun part1(input: List<String>, multiplier: Long= 1L): Long {
        val galaxies = input.galaxies()
        val (rows, cols) = input.rowCols()

        return galaxies.combinations(2).map { (g1, g2) ->
            val distance: Long = g1.manhattanDistance(g2)
            val expansion: Long = (abs(rows[g1.y.toInt()] - rows[g2.y.toInt()]) + abs(cols[g1.x.toInt()] - cols[g2.x.toInt()]))*multiplier
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