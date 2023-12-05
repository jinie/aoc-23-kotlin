import kotlin.math.pow

fun main() {

    fun parseInput(input: List<String>): Map<Int,Int>{
        return input.associate { line ->
            var (card, numberSource) = line.split(":").also { it.first().removePrefix("Card ").trim() }
            card = card.removePrefix("Card ").trim()
            val (winners, stakes) = numberSource.split("|")
                .map { it2 -> it2.split(" ").mapNotNull { if (it.isNotEmpty()) it.trim().toInt() else null }.toSet() }
            card.toInt() to winners.intersect(stakes).size
        }
    }

    fun part1(input: Map<Int,Int>): Int {
        return input.values.sumOf { data ->
            2.0.pow(data -1).toInt()
        }
    }

    fun part2(input: Map<Int,Int>): Int {
        val cardCt = input.keys.associateWith { 1 }.toMutableMap()
        input.forEach{ (cardNo, data) ->
            val ct = if(data+cardNo > input.keys.max()) input.keys.max() else data+cardNo
            (cardNo+1 .. ct).forEach {
                cardCt[it] = cardCt.getOrDefault(it,0) + (1 * cardCt[cardNo]!!)
            }
        }
        return cardCt.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = parseInput(readInput("Day04_test"))
    check(part1(testInput) == 13)
    check(part2(testInput)== 30)
    measureTimeMillisPrint {
        val input = parseInput(readInput("Day04"))

        println(part1(input))
        println(part2(input))
    }
}
