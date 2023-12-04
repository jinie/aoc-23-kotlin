fun main() {

    fun parseInput(input: List<String>): Map<Int,Pair<Set<Int>, Set<Int>>>{
        val ret = mutableMapOf<Int,Pair<Set<Int>, Set<Int>>>()
        input.forEach {line ->
            var (card, numberSource) = line.split(":")
            card = card.removePrefix("Card ").strip()
            val (winners,stakes) = numberSource.split("|").map { it.split(" ").mapNotNull { if(it.isNotEmpty()) it.strip().toInt() else null }.toSet() }
            ret[card.toInt()] = Pair(winners, stakes)
        }
        return ret
    }

    fun part1(input: Map<Int,Pair<Set<Int>, Set<Int>>>): Int {
        var ret = 0
        input.forEach { (_, data) ->
            val common = data.first.intersect(data.second)
            var tret = 1
            if(common.isNotEmpty()) {
                for(i in 1 until common.size)
                    tret *= 2
                ret += tret
            }
        }
        return ret
    }

    fun part2(input: Map<Int,Pair<Set<Int>, Set<Int>>>): Int {
        val cardCt = mutableMapOf<Int, Int>()
        input.keys.forEach { cardCt[it] = 1 }
        input.forEach{ (cardNo, data) ->
            val common = data.first.intersect(data.second)
            for(i in cardNo+1 until cardNo+1+common.size)
                if(cardCt.keys.contains(i))
                    cardCt[i] = cardCt[i]!! + (1 * cardCt[cardNo]!!)
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
