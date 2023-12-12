import utils.measureTimeMillisPrint
import utils.readInput

fun main() {

    fun String.parseInput(): Pair<String,List<Int>>{
          return this.split(" ").let { it -> Pair(it.first(),it.last().split(",").map{ it.toInt()}) }
    }

    val cache = mutableMapOf<Pair<List<Int>, String>, Long>()
    fun solve(pixels: String, groups: List<Int>): Long{
        return when {
            pixels.isEmpty() -> if(groups.isEmpty()) 1 else 0 // "" (,) or "" (1,)
            pixels.startsWith('.') -> solve(pixels.trim('.'), groups) // ".###?" (4,)
            pixels.startsWith('?') -> { //"?###?" (4,)
                 solve(pixels.replaceFirst('?', '.'), groups) +
                        solve(pixels.replaceFirst('?','#'), groups)
            }
            pixels.startsWith('#') -> {
                cache[groups to pixels]?.let { return it }
                when {
                    groups.isEmpty() -> 0 // "##" (,)
                    pixels.length < groups.first() -> 0 // "##" (3,)
                    pixels.substring(0,groups.first()).any { it == '.' } -> 0 // "##.???" (3,1)
                    groups.size > 1 -> {
                        if ((pixels.length < (groups.first() + 1)) || (pixels[groups.first()] == '#')) 0
                        else solve(pixels.substring(groups.first() + 1), groups.drop(1)) // "##.???" (2,1) -> "???" (1,)
                    }
                    else -> solve(pixels.substring(groups.first()), groups.drop(1)) //  "##.???" (2,) -> ".???" (,)
                }.also { cache[groups to pixels] = it }
            }
            else -> error("No branches possible")
        }
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val (pixels, groups) = line.parseInput()
            solve(pixels, groups)
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            var (pixels, groups) = line.parseInput()
            pixels = (0 until 5).joinToString("?") { pixels }
            groups = (0 until 5).flatMap { groups }
            solve(pixels, groups)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 21L)
    check(part2(testInput) == 525152L)
    measureTimeMillisPrint {
        val input = readInput("Day12")

        println(part1(input))
        println(part2(input))
    }
}
