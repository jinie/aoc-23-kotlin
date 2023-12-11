import utils.measureTimeMillisPrint
import utils.readInput

fun main() {

    data class Play(val hand: String, val bid:Long) : Comparable<Play>{
        val cardinality = hand.map { it }.toSet().size
        var joker = false
        fun rank(): Int {
            var rank = -1
            val tHand = if(joker) hand.replace("J","").toString() else hand
            when (cardinality) {
                1 //5 of a kind
                -> {
                    rank = 7
                }
                2 -> {
                    rank = when  {
                        tHand.groupBy{it}.any { it.value.size == 4} -> 6 //4 of a kind
                        tHand.groupBy{it}.any {it.value.size == 3} && tHand.groupBy {it}.any {it.value.size == 2} -> 5 //Full house
                        else -> 0
                    }
                }
                3 -> {
                    rank = when {
                        tHand.groupBy{it}.any {it.value.size == 3} -> 4 // 3 of a kind
                        tHand.groupBy{it}.any {it.value.size == 2} -> 3 // Two Pairs
                        else -> 0
                    }
                }
                4 -> {
                    rank = when {
                        tHand.groupBy{it}.any {it.value.size == 2}  -> 2// One Pair
                        else -> 0
                    }
                }
                5 -> {
                    rank = 1
                }
            }

            if(joker){
                rank = when {
                    rank == 6 -> 7
                    rank == 5 -> 6
                    rank == 4 -> 5
                    rank == 3 -> 4
                    rank == 2 -> 3
                    rank == 1 -> 2
                    else -> 0
                }
            }
            return rank
        }

        fun compareHands(hand1: String, hand2: String, order: String) = hand1.zip(hand2).find {
            it.first != it.second
        }?.let {
            if (order.indexOf(it.first) < order.indexOf(it.second)) -1 else 1
        } ?: 0
        override fun compareTo(other: Play): Int {
            val trank = this.rank()
            val orank = other.rank()
            if(trank > orank)
                return 1
            else if (trank < orank)
                return -1
            else {
                return compareHands(this.hand, other.hand,"23456789TJQKA")
            }

        }
    }

    fun rank(hand:String): Long{
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

    fun parseInput2(input: List<String>): List<Play> {
        return input.map {
            it.split(" ")
        }.map {
            Play(it[0], it[1].toLong())
        }.toList()
    }


    fun part1_2(input:List<String>): Long {
        val handList = parseInput2(input)
        return handList.sortedWith{ play, other -> play.compareTo(other) }.mapIndexed { index, play -> (index + 1) * play.bid }.sum()
    }

    fun part2_2(input:List<String>): Long {
        val handList = parseInput2(input)
        for(it in handList)
            it.joker = true
        return handList.sortedWith{ play, other -> play.compareTo(other) }.mapIndexed { index, play -> (index + 1) * play.bid }.sum()
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
    check(part1_2(testInput) == 6440L)
    check(part2(testInput) == 5905L)
    check(part2_2(testInput) == 5905L)
    measureTimeMillisPrint {
        val input = readInput("Day07")

        println(part1_2(input))
        println(part2_2(input))
    }
}
