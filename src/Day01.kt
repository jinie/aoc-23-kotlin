fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val values = it.split("").mapNotNull { it2 -> it2.toIntOrNull() }
            values.first() * 10 + values.last()
        }
    }

    val strToDigit = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    fun part2(input: List<String>): Int {
        val valueSet =  strToDigit.keys + strToDigit.values
        return input.sumOf {
            var ret = ""
            val (_, firstValue) = it.findAnyOf(valueSet) ?: error("Could not find first Digit")
            val (_, lastValue) = it.findLastAnyOf(valueSet) ?: error("Could not find last Digit")
            listOf(firstValue, lastValue).map { value ->
                ret += if (value.toIntOrNull() != null) {
                    value
                } else {
                    strToDigit[value]!!
                }
            }
            ret.toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    measureTimeMillisPrint {
        val input = readInput("Day01")

        println(part1(input))
        println(part2(input))
    }
}
