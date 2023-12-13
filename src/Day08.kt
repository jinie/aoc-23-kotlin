import utils.measureTimeMillisPrint
import utils.readInput

fun main() {
    fun solve(startPos:String, directions:String, input:Map<String, Pair<String, String>>): Long {
        var curPos = startPos
        var steps = 0L
        while (true) {
            for (ch in directions) {
                steps++
                val nPos = when (ch) {
                    'L' -> input[curPos]!!.first
                    'R' -> input[curPos]!!.second
                    else -> error("Should never happen")
                }

                if (nPos == curPos) {
                    error("Infinite loop")
                }
                curPos = nPos

                if (curPos.endsWith("Z"))
                    return steps
            }
        }
    }

    fun part1(directions: String, input: Map<String, Pair<String, String>>): Long {
        return solve(input.keys.first(), directions, input)
    }

    fun part2(directions: String, input: Map<String, Pair<String, String>>): Long {
        val startPositions = input.keys.filter { it.endsWith("A") }.toMutableList()
        return startPositions.map { solve(it, directions, input) }.findLCM()
    }

    fun parseInput(input: List<String>): Pair<String, Map<String, Pair<String, String>>>{
        input.take(2)
        return Pair(input.first(), input.asSequence().drop(2).associate {
            val key = it.split("=").first().trim()
            val value = it.split("=").last().trim().removePrefix("(").removeSuffix(")").split(",")
            key to Pair(value.first().trim(), value.last().trim())
        })
    }

    // test if implementation meets criteria from the description, like:
    var testInput = parseInput(readInput("Day08_test"))
    check(part1(testInput.first, testInput.second) == 2L)
    testInput = parseInput(readInput("Day08_test2"))
    check(part2(testInput.first, testInput.second) == 6L)
    measureTimeMillisPrint {
        val input = parseInput(readInput("Day08"))

        println(part1(input.first, input.second))
        println(part2(input.first, input.second))
    }
}
