import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {


    /**
     * t = T - B    (1)
     * Where:
     * t is travel time
     * T is race time,
     * B button pressed time)
     *
     * Where
     * D is the traveled distance
     * t is the travel time
     * B is the button pressed time
     *
     * Substituting (1) in (2) and simplifying we get
     *
     * D = (T - B) * B
     * D = T*B - B^2      (3)
     * B^2 - T*B - D = 0
     * Now we can use the quadratic formula to solve for B, and setting D to the record distance + 1
     *
     * B1 = (T + SQRT(T*T - 4 * D))/2
     * B2 = (T - SQRT(T*T - 4 * D))/2
     *
     * Number of Races that set a new record B1 - B2 which is the number of integer solutions between the two record point solutions.
     */
    fun possibleWinningStrategies(time:Double, distance:Long): Long{
        val d = sqrt(time*time - distance*4)
        val from = floor((time - d) / 2)
        val to = ceil((time + d) / 2)
        return (to-from-1).toLong()
    }

    fun parseInput(input: List<String>): List<Pair<Long,Long>>{
        val times = input.first().split(":").last().split(" ").map { it.trim() }.filterNot { it.isEmpty() }.map{it.toLong()}
        val distances = input.last().split(":").last().split(" ").map {it.trim()}.filterNot { it.isEmpty() }.map { it.toLong() }
        val ret = mutableListOf<Pair<Long,Long>>()
        times.forEachIndexed{ idx, it ->
            ret.add(Pair(it, distances[idx]))
        }
        return ret
    }

    fun part1(input: List<Pair<Long,Long>>): Long {
       return input.map {
            possibleWinningStrategies(it.first.toDouble(), it.second)
        }.reduce { acc, i ->  acc * i}.toLong()
    }

    fun part2(input: List<Pair<Long,Long>>): Long {
        val time = input.map { it.first.toString() }.reduce { acc, s -> acc + s }.toLong()
        val distance = input.map { it.second.toString() }.reduce { acc, s -> acc + s }.toLong()
        return possibleWinningStrategies(time.toDouble(), distance)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = parseInput(readInput("Day06_test"))
    check(part1(testInput) == 288L)
    measureTimeMillisPrint {
        val input = parseInput(readInput("Day06"))

        println(part1(input))
        println(part2(input))
    }
}
