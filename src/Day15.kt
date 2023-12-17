import utils.measureTimeMillisPrint
import utils.readInputString

fun main() {

    fun hash(input: String): Int = input.fold(0) { hash, c -> ((hash + c.code) * 17) % 256 }

    fun part1(input: String): Int {
       return input.split(",").sumOf { hash(it) }
    }

    fun part2(input: String): Int {
        return input.split(",").fold(Array(256) { mutableMapOf<String, Int>() }) { acc, line ->
            val (value, focalLength) = line.split("=", "-")
            when {
                "-" in line -> acc[hash(value)] -= value
                else -> acc[hash(value)][value] = focalLength.toInt()
            }
            acc
        }.withIndex().sumOf { (i, map) -> (i + 1) * map.values.withIndex().sumOf { (j, value) -> (j + 1) * value } }    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputString("Day15_test")
    check(part1(testInput) == 1320)
    check(part2(testInput) == 145)
    measureTimeMillisPrint {
        val input = readInputString("Day15")
        println(part1(input))
        println(part2(input))
    }
}
