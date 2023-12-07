fun main() {

    fun rank(hand:String): Long{
        if(hand.compareTo(hand.replace("23456789TQKA", "")) != 0) {
            return 0
        }
        val uniqueCards = hand.replace("23456789TQKA", "")
            .toSet()
            .joinToString("")
            .count()
        val cardCounts = hand
            .groupingBy { it }
            .eachCount()
        val rank: Long = when {
            cardCounts.any { it.value == 5 } -> 6
            cardCounts.any { it.value == 4 } -> 5
            uniqueCards == 2 && cardCounts.any { it.value == 3 } -> 4
            cardCounts.any { it.value == 3 } -> 3
            uniqueCards == 3 && cardCounts.any { it.value == 2 } -> 2
            uniqueCards == 4 -> 1
            else -> 0
        }
        return rank
    }

    fun compareHands(hand1: String, hand2: String, order: String) = hand1.zip(hand2).find {
        it.first != it.second
    }?.let {
        if (order.indexOf(it.first) < order.indexOf(it.second)) -1 else 1
    } ?: 0


    fun parseInput(input: List<String>): List<Pair<String,Long>> {
        return input.map {
            it.split(" ")
        }.map {
            Pair(it[0],it[1].toLong())
        }.toList()
    }

    fun part1(input: List<String>): Long {
        // Create a list of hands, sorted by rank, then by sortRank
        val handList = parseInput(input)
        return handList.asSequence().map {
            Triple(it.first, it.second, rank(it.first))
        }.sortedWith { left, right ->
            if (left.third != right.third) left.third.compareTo(right.third)
            else compareHands(left.first, right.first, "23456789TJQKA")
        }.map {
            it.first to it.second.toInt()
        }.mapIndexed { i, it ->
            (i + 1) * it.second
        }.sum().toLong()
    }

    fun part2(input: List<String>): Long {
        val handList = parseInput(input)
        return handList.asSequence().map {
            Triple(it.first, it.second, rank(it.first.map {
                if (it == 'J') "23456789TQKA" else it.toString()
            }.joinToString("")))
        }.sortedWith { left, right ->
            if (left.third != right.third) left.third.compareTo(right.third)
            else compareHands(left.first, right.first, "J23456789TQKA")
        }.map {
            it.first to it.second.toInt()
        }.mapIndexed {
                     i, it -> (i + 1) * it.second
        }.sum().toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440L)
    check(part2(testInput) == 5905L)
    measureTimeMillisPrint {
        val input = readInput("Day07")

        println(part1(input))
        println(part2(input))
    }
}
