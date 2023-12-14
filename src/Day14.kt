import utils.measureTimeMillisPrint
import utils.readInput
import kotlin.streams.toList

fun main() {

    fun List<String>.parseInput(): MutableList<StringBuilder> {
        return this.map { StringBuilder(it) }.toMutableList()
    }

    fun MutableList<StringBuilder>.transpose(): MutableList<StringBuilder>{
        return this.map { it.chars().toList() }.transpose().map { StringBuilder(it.toString()) }.toMutableList()
    }

    fun MutableList<StringBuilder>.moveRocks(direction:Int = -1): MutableList<StringBuilder> {
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
        return this
    }

    fun part1(input: List<String>): Long {
        return input.parseInput().moveRocks().mapIndexed { index, row ->
            row.count { it == 'O' } * (input.size - index)
        }.sum().toLong()
    }

    fun part2(input: List<String>): Long {
        var rocks = input.parseInput()
        for(i in 0 .. (1000000000/4)){
            rocks = rocks.moveRocks(-1)
            rocks = rocks.transpose()
            rocks = rocks.moveRocks(-1)
            rocks = rocks.transpose()
            rocks = rocks.moveRocks(1)
            rocks = rocks.transpose()
            rocks = rocks.moveRocks(1)
            rocks = rocks.transpose()
        }
        return rocks[0].count{ it == 'O'} * 10L
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 136L)
    check(part2(testInput) == 64L)
    measureTimeMillisPrint {
        val input = readInput("Day14")

        println(part1(input))
        println(part2(input))
    }
}
