fun main() {

    fun part1(directions: String, input: Map<String, Pair<String, String>>): Long {
        var curPos: String = input.keys.first()
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

                if (curPos == "ZZZ")
                    return steps

            }
        }
    }

    fun part2(directions: String, input: Map<String, Pair<String, String>>): Long {
        val startPositions = input.keys.filter { it.endsWith("A") }.toMutableList()
        val stepList = mutableListOf<Long>()

        for(idx in 0 until startPositions.size){
            var steps = 0L
            var curPos = startPositions[idx]
            while (true) {
                for (ch in directions) {
                    val nPos = when (ch) {
                        'L' -> input[curPos]!!.first
                        'R' -> input[curPos]!!.second
                        else -> error("Should never happen")
                    }

                    if (nPos == curPos) {
                        error("Infinite loop")
                    }
                    curPos = nPos
                    steps++

                    if(curPos.endsWith("Z")) {
                        stepList.add(steps)
                        break
                    }
                }
                if(curPos.endsWith("Z")) {
                    stepList.add(steps)
                    break
                }
            }
        }
        return findLCMOfListOfNumbers(stepList)
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
