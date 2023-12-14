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

    val cache: MutableMap<String, Long> = mutableMapOf()

    fun List<MutableList<Rock>>.rotate90degrees(): List<MutableList<Rock>>{
        val ret = mutableListOf<MutableList<Rock>>()
        for (x in 0..this[0].lastIndex) {
            val row = mutableListOf<Rock>()
            for (y in (this.lastIndex) downTo 0) {
                row.add(this[y][x])
            }
            ret.add(row)
        }
        return ret
    }

    fun List<MutableList<Rock>>.lowestEmptyYFromCoord(x: Int, y: Int): Int {
        var ty = y
        while (ty >= 0 && this[ty][x] == Rock.None) {
            ty--
        }
        return ty+1
    }

    fun List<MutableList<Rock>>.moveRocks(): List<MutableList<Rock>> {
        this.forEachIndexed { y, _ ->
           this.forEachIndexed { x, _ ->
               if(this[y][x] == Rock.Rounded) {
                   this[y][x] = Rock.None
                   this[this.lowestEmptyYFromCoord(x, y-1)][x] = Rock.Rounded
               }
            }
        }

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
        var i =0L
        var skip = true
        while(i < 1_000_000_000) {
            for(j in 0 until 4) {
                rocks = rocks.moveRocks()
                rocks = rocks.rotate90degrees()
            }
            if(skip) {
                val key = rocks.joinToString("")
                when(key){
                    in cache -> {
                        val cycle = i - cache.getValue(key)
                        val cyclesLeft = (1_000_000_000 - i) / cycle
                        i += cycle * cyclesLeft
                        skip = false
                    }
                    else -> cache[key] = i
                }
            }
            i++
        }
        return rocks.mapIndexed { index, row ->
            row.count { it == Rock.Rounded } * (input.size - index)
        }.sum().toLong()
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
