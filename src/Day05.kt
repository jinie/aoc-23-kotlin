fun main() {


    data class SourceRange(var sourceRangeStart: Long, var destinationRangeStart: Long, var length: Long) {

        val offset = (destinationRangeStart - sourceRangeStart)
        val sourceRangeEnd = sourceRangeStart + length

        fun mapSeed(source: Long): Long {
            val ret = if (source in sourceRangeStart..sourceRangeStart+length) {
                source + offset
            } else
                source
            return ret
        }

        fun reverseMapSeed(source: Long): Long {
            val ret = if (source in destinationRangeStart..destinationRangeStart+length) {
                source - offset
            } else
                source
            return ret
        }

        fun matches(source: Long): Boolean {
            return (source in sourceRangeStart..sourceRangeEnd)
        }

        fun reverseMatches(source: Long): Boolean {
            return (source in destinationRangeStart..destinationRangeStart+length)
        }

    }

    data class Almanac(var sourceRanges: MutableMap<String, MutableList<SourceRange>>) {
        private var order: List<String> =
            listOf("soil", "fertilizer", "water", "light", "temperature", "humidity", "location")

        fun mapSeed(seed: Long): Long {
            var plot: Long = seed
            for (s in order) {
                if (sourceRanges[s]?.any { it.matches(plot) } == true) {
                    plot = sourceRanges[s]!!.first { it.matches(plot) }.mapSeed(plot)
                }
            }
            return plot
        }

        fun reverseMapSeed(seed: Long): Long {
            var plot: Long = seed
            for (s in order.reversed()) {
                if (sourceRanges[s]?.any { it.reverseMatches(plot) } == true) {
                    plot = sourceRanges[s]!!.first { it.reverseMatches(plot) }.reverseMapSeed(plot)
                }
            }
            return plot
        }
    }

    fun parseInput(input: List<String>): Pair<List<Long>, Almanac> {
        val ret = Almanac(mutableMapOf())
        val seeds =
            input[0].split(":")[1].split(" ").map { it.trim() }.filterNot { it.isEmpty() }.map { it.toLong() }
                .toMutableList()
        val mapRe = "([a-zA-Z\\-]+)\\s*map:".toRegex()
        val lineRe = "^(\\d+) (\\d+) (\\d+)\\s?\$".toRegex()
        var curMap = ""
        input.forEach { line ->
            if (mapRe.matches(line)) {
                curMap = mapRe.find(line)?.groupValues?.get(1)?.split("-")?.last() ?: error("No match found")
                ret.sourceRanges[curMap] = mutableListOf()
            } else if (lineRe.matches(line)) {
                lineRe.findAll(line).forEach {
                    val ranges =
                        it.value.split(" ").map { it.trim() }.filterNot { it.isEmpty() }.map { it.toLong() }
                    val value = SourceRange(ranges[1], ranges[0] , ranges[2])
                    ret.sourceRanges[curMap]?.add(value)
                }
            }
        }
        return Pair(seeds, ret)
    }

    fun part1(input: Pair<List<Long>, Almanac>): Long {
        val seeds = input.first
        val almanacs = input.second
        return seeds.minOf { almanacs.mapSeed(it) }
    }


    fun part2(input: Pair<List<Long>, Almanac>): Long {
        val almanacs = input.second
        val ranges = input.first.windowed(2).map { LongRange(it[0], it[0]+it[1]) }.toMutableList()
        val max = ranges.maxOf { it.last }
        val p2val: Long = bSearch(0, max, true) { value ->
            ranges.any { it.contains(almanacs.reverseMapSeed(value)) }
        } ?: error("No solution found")

        return p2val
    }

    // test if implementation meets criteria from the description, like:
    val testInput = parseInput(readInput("Day05_test"))
    check(part1(testInput) == 35L)
    measureTimeMillisPrint()
    {
        val input = parseInput(readInput("Day05"))

        val p1res = part1(input)
        println(p1res)
        println(part2(input))
    }
}
