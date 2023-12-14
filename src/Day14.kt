import utils.measureTimeMillisPrint
import utils.readInput

enum class Rock { None, Rounded, Cubic }
fun main() {


    fun List<String>.parseInput(): List<MutableList<Rock>> {
        return this.map { it.map {
            when (it) {
                '.' -> Rock.None
                'O' -> Rock.Rounded
                else -> Rock.Cubic
            }
        }.toMutableList() }
    }

    val cache: MutableMap<String, List<MutableList<Rock>>> = mutableMapOf()

    fun List<MutableList<Rock>>.lowestEmptyYFromCoord(x: Int, y: Int): Int {
        var ty = y
        while (ty >= 0 && this[ty][x] == Rock.None) {
            ty--
        }
        return ty+1
    }

    fun List<MutableList<Rock>>.moveRocks(): List<MutableList<Rock>> {
        var key = this.joinToString("")
        if (cache.containsKey(key))
            return cache[key]!!
        this.forEachIndexed { y, _ ->
           this.forEachIndexed { x, _ ->
               if(this[y][x] == Rock.Rounded) {
                   this[y][x] = Rock.None
                   this[this.lowestEmptyYFromCoord(x, y-1)][x] = Rock.Rounded
               }
            }
        }

        key = this.joinToString("")
        cache[key] = this
        return this
    }

    fun part1(input: List<String>): Long {
        val ret = input.parseInput().moveRocks().mapIndexed { index, row ->
            row.count { it == Rock.Rounded } * (input.size - index)
        }.sum().toLong()
        return ret
    }

    fun part2(input: List<String>): Long {
        var rocks = input.parseInput()
        for(i in 0 until  (100000000)){
            rocks = rocks.moveRocks()
            rocks = rocks.transpose().map { it.toMutableList() }
        }
        return rocks[0].count{ it == Rock.Rounded} * 10L
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 136L)
    //check(part2(testInput) == 64L)
    measureTimeMillisPrint {
        val input = readInput("Day14")

        println(part1(input))
        //println(part2(input))
    }
}
