import utils.measureTimeMillisPrint
import utils.readInput

fun main() {

    fun String.delta(other: String): Int {
        return this.mapIndexed { index, c ->
            when {
                other[index] != c -> 1
                else -> 0
            }
        }.sum()
    }

    fun List<String>.isReflection(): Int {
        for(i in 1 .. this.lastIndex){
            var match = true
            for(j in 1 .. i){
                if(i+j-1 > this.lastIndex)
                    break
                //val delta = this[i+j-1].delta(this[i-j])
                if(this[i+j-1] != this[i-j] ) {
                        match = false
                        break
                }
            }
            if(match)
                return i
        }
        return 0
    }

    fun List<String>.transpose(): List<String> {
        return this.map { it.toList() }.transpose().map { it.toString() }
    }

    fun part1(input: List<String>): Int {
        val groups = input.splitEmpty()
        val ret = groups.sumOf {
            val res = it.transpose().isReflection()
            if(res == 0) it.isReflection()*100 else res
        }
        return ret
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 405)
    measureTimeMillisPrint {
        val input = readInput("Day13")

        println(part1(input))
        println(part2(input))
    }
}
