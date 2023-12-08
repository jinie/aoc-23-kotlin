fun main() {

    fun part1(directions: String, input: Map<String, Pair<String, String>>): Long {
        var curPos: String = input.keys.first()
        var steps = 0L
        while (true) {
            for (ch in directions) {
                val nPos = when {
                    ch == 'L' -> input[curPos]!!.first
                    ch == 'R' -> input[curPos]!!.second
                    else -> error("Should never happen")
                }

                if (nPos == curPos) {
                    error("Infinite loop")
                }
                curPos = nPos
                steps++
                if (curPos == "ZZZ")
                    return steps

            }
        }
    }

    fun part2(directions: String, input: Map<String, Pair<String, String>>): Int {
        return 0
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
    val testInput = parseInput(readInput("Day08_test"))
    check(part1(testInput.first, testInput.second) == 2L)
    measureTimeMillisPrint {
        val input = parseInput(readInput("Day08"))

        println(part1(input.first, input.second))
        println(part2(input.first, input.second))
    }
}
