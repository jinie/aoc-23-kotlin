fun main() {

    data class Number(val value: Any?, val points: List<Point2d>, val isSymbol: Boolean = false){
        fun contains(point: Point2d): Boolean {
            return points.contains(point)
        }

        fun neighbours(): List<Point2d> {
            return points.flatMap { point ->
                point.neighbors().filter { neighbour ->
                    !points.contains(neighbour)
                }
            }
        }
    }

    fun parseInput(input: List<String>): List<Number> {
        val numbers = mutableListOf<Number>()

        input.forEachIndexed { y, line ->
            var currentNumber = mutableListOf<Point2d>()
            var currentString = ""
            line.forEachIndexed { x, char ->
                if (char.isDigit()) {
                    currentNumber.add(Point2d(x, y))
                    currentString += char
                } else {
                    if (currentNumber.isNotEmpty()) {
                        numbers.add(Number(currentString.toInt(), currentNumber))
                        currentNumber = mutableListOf()
                        currentString = ""
                    }
                    if (char != '.'){
                        numbers.add(Number(char, listOf(Point2d(x, y)), true))
                    }
                }
            }
            if (currentNumber.isNotEmpty()) {
                numbers.add(Number(currentString.toInt(), currentNumber))
            }
        }
        return numbers
    }

    fun part1(input: List<Number>): Int {
        val symbols = input.filter { it.isSymbol }.flatMap { symbol ->
            symbol.neighbours().filter { point ->
                input.any { number -> number.contains(point) }
            }
        }.distinct()

        return input.filter { number -> symbols.any { symbol -> number.contains(symbol) } }
            .sumOf { number -> number.value as Int }

    }

    /**
     * Find all numbers that are separated by a * symbol
     * Multiply them and add them to the sum
     */
    fun part2(input: List<Number>): Int {
        return input.filter { it.isSymbol && it.value == '*' }.sumOf {
            val neighbours = it.neighbours()
            val numbers = input.filter { number -> neighbours.any { neighbour -> number.contains(neighbour) } }
            if (numbers.size == 2) {
                numbers.map { number -> number.value as Int }.reduce { acc, i -> acc * i }
            }else {
                0
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = parseInput(readInput("Day03_test"))
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)
    measureTimeMillisPrint {
        val input = parseInput(readInput("Day03"))

        println(part1(input))
        println(part2(input))
    }
}
