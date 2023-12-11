import utils.measureTimeMillisPrint
import utils.readInput

fun main() {

    fun List<Int>.predict() = generateSequence(this) {
            it.windowed(2) { (left, right) -> right - left } }
            .takeWhile { numbers -> numbers.any { it != 0 } }
            .sumOf { it.last() }

    fun part1(input: List<List<Int>>): Int = input.sumOf{ it.predict()}
    fun part2(input: List<List<Int>>): Int = input.sumOf{ it.reversed().predict()}
    fun List<String>.parseInput(): List<List<Int>> = this.map { it.split(' ').map(String::toInt) }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test").parseInput()
    check(part1(testInput) == 114)
    measureTimeMillisPrint {
        val input = readInput("Day09").parseInput()

        println(part1(input))
        println(part2(input))
    }
}