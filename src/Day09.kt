fun main() {

    data class History(val readings: List<Int>) {
        fun predict(values: List<Int>) = generateSequence(values) {
            it.windowed(2) { (left, right) -> right - left } }
            .takeWhile { numbers -> numbers.any { it != 0 } }
            .sumOf { it.last() }

        val next: Int by lazy { predict(readings) }

        val previous: Int by lazy { predict(readings.reversed()) }
    }


    fun parseInput(input: List<String>): List<History> = input.map {
        it.split(' ').map(String::toInt)
        }.toList()
        .map(::History)


    fun part1(input: List<History>): Int {
        return input.sumOf(History::next)
    }

    fun part2(input: List<History>): Int {
        return input.sumOf(History::previous)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = parseInput(readInput("Day09_test"))
    check(part1(testInput) == 114)
    measureTimeMillisPrint {
        val input = parseInput(readInput("Day09"))

        println(part1(input))
        println(part2(input))
    }
}
