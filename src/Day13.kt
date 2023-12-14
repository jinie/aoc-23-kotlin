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

    fun List<String>.isReflection(notAllowed: Int = -1): Int {
        for(i in 1 .. this.lastIndex){
            var match = true
            for(j in 1 .. i){
                if(i+j-1 > this.lastIndex)
                    break
                if(this[i+j-1] != this[i-j]) {
                        match = false
                        break
                }
            }

            if(match && i != notAllowed)
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
        val groups = input.splitEmpty().toMutableList()
        return groups.sumOf { group ->
            var sum = 0
            val tGrp = group.toMutableList()
            var done = false
            for (i in group.indices) {
                val sb = StringBuilder(group[i])
                for (j in sb.indices) {
                    sb[j] = if (sb[j] == '.') '#' else '.'
                    tGrp[i] = sb.toString()
                    var ret = tGrp.transpose().isReflection(group.transpose().isReflection())
                    if (ret == 0)
                        ret = tGrp.isReflection(group.isReflection()) * 100
                    if (ret != 0) {
                        sum += ret
                        done = true
                        break
                    }
                    sb[j] = if (sb[j] == '.') '#' else '.'
                }
                if(done)
                    break
            }
            sum
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 405)
    check(part2(testInput) == 400)
    measureTimeMillisPrint {
        val input = readInput("Day13")

        println(part1(input))
        println(part2(input))
    }
}
