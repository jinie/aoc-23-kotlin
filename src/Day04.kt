import kotlin.math.pow

fun main() {

    fun parseInput(input: List<String>): Map<Int,Pair<Set<Int>, Set<Int>>>{
        return input.map {line ->
            var (card, numberSource) = line.split(":")
            card = card.removePrefix("Card ").strip()
            val (winners,stakes) = numberSource.split("|").map { it.split(" ").mapNotNull { if(it.isNotEmpty()) it.strip().toInt() else null }.toSet() }
            card.toInt() to Pair(winners, stakes)
        }.toMap()
    }

    fun part1(input: Map<Int,Pair<Set<Int>, Set<Int>>>): Int {
        return input.map { (_, data) ->
            data.first.intersect(data.second).run { if(this.isNotEmpty()) 2.0.pow(this.size -1).toInt() else 0 }
        }.sum()
    }

    fun part2(input: Map<Int,Pair<Set<Int>, Set<Int>>>): Int {
        val cardCt = input.keys.associateWith { 1 }.toMutableMap()
        input.forEach{ (cardNo, data) ->
            val ct = data.first.intersect(data.second).size+cardNo
            (cardNo+1 .. ct).forEach {
                if (cardCt.keys.contains(it))
                    cardCt[it] = cardCt[it]!! + (1 * cardCt[cardNo]!!)
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
