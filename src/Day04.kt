import kotlin.math.pow

fun main() {

    fun parseInput(input: List<String>): Map<Int,Int>{
        return input.map {line ->
            var (card, numberSource) = line.split(":").also { it.first().removePrefix("Card ").strip()}
            card = card.removePrefix("Card ").strip()
            val (winners,stakes) = numberSource.split("|").map { it.split(" ").mapNotNull { if(it.isNotEmpty()) it.strip().toInt() else null }.toSet() }
            card.toInt() to winners.intersect(stakes).size
        }.toMap()
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
