import utils.measureTimeMillisPrint
import utils.readInput

fun main() {

    fun List<String>.parseInput(): MutableList<StringBuilder> {
        return this.map { StringBuilder(it) }.toMutableList()
    }

    fun MutableList<StringBuilder>.moveRocks(direction:Int = -1): Long {
        for(x in this[0].indices) {
            var done = false
            while(!done) {
                var y = 1
                var moved = false
                do {
                    if (this[y][x] == 'O') {
                        if (this[y + direction][x] == '.') {
                            this[y + direction][x] = 'O'
                            this[y][x] = '.'
                            moved = true
                            break
                        }
                    }
                    y++
                } while (y < this.size)
                if (!moved)
                    done = true
            }
        }
        return this.mapIndexed { index, row ->
            row.count { it == 'O' } * (this.size - index)
        }.sum().toLong()

    }

    fun part1(input: List<String>): Long {
        return input.parseInput().moveRocks()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 136L)
    measureTimeMillisPrint {
        val input = readInput("Day14")

        println(part1(input))
        println(part2(input))
    }
}
