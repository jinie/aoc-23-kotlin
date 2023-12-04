fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 142)
    measureTimeMillisPrint {
        val input = readInput("Day05")

        println(part1(input))
        println(part2(input))
    }
}
