fun main() {


    data class SourceRange(val sourceRangeStart:Long, val sourceRangeEnd: Long, val offset:Long){
        fun mapSeed(source: Long): Long {
            val ret = if (source in sourceRangeStart..sourceRangeEnd) {
                source + offset
            } else
                source
            println("source: $source, rangeStart:$sourceRangeStart, end:$sourceRangeEnd, offset:${offset}, destination=$ret")
            return ret
        }

        fun matches(source: Long): Boolean{
            return (source in sourceRangeStart..sourceRangeEnd)
        }
    }

    data class Almanac(val sourceRanges:MutableMap<String,MutableList<SourceRange>>){
        private val order:List<String> = listOf("soil","fertilizer","water","light","temperature","humidity","location")
        fun mapSeed(seed: Long): Long{
            var plot:Long = seed
            println("seed: $seed")
            for(s in order){
                if(sourceRanges[s]?.any { it.matches(plot)} == true) {
                    plot = sourceRanges[s]!!.filter { it.matches(plot) }.map { it.mapSeed(plot) }.first()
                }
                println("$s => $plot")
            }
            return plot
        }
    }

    fun parseInput(input: List<String>): Pair<List<Long>,Almanac>{
        val ret = Almanac(mutableMapOf())
        val seeds = input[0].split(":")[1].split(" ").map { it.trim() }.filterNot { it.isEmpty() }.map { it.toLong() }.toMutableList()
        val mapRe = "([a-zA-Z\\-]+)\\s*map:".toRegex()
        val lineRe = "^(\\d+) (\\d+) (\\d+)\\s?\$".toRegex()
        var curMap: String = ""
        input.forEach { line ->
            if(mapRe.matches(line)){
                curMap = mapRe.find(line)?.groupValues?.get(1)?.split("-")?.last() ?: error("No match found")
                ret.sourceRanges[curMap]= mutableListOf()
            }else if(lineRe.matches(line)){
                lineRe.findAll(line).forEach {
                    val ranges = it.value.split(" ").map { it.trim() }.filterNot { it.isEmpty() }.map { it.toLong() }
                    val value = SourceRange(ranges[1], ranges[1] + ranges[2], ranges[0] - ranges[1])
                    ret.sourceRanges[curMap]?.add(value)
                }
            }
        }
        return Pair(seeds,ret)
    }

    fun part1(input: Pair<List<Long>,Almanac>): Long {
        val seeds = input.first
        val almanacs = input.second
        val ret = seeds.map { almanacs.mapSeed(it) }.min()
        return ret
    }

    fun part2(input: Pair<List<Long>,Almanac>): Long {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = parseInput(readInput("Day05_test"))
    check(part1(testInput) == 35L)
    measureTimeMillisPrint {
        val input = parseInput(readInput("Day05"))

        println(part1(input))
        println(part2(input))
    }
}
