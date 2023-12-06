fun main() {

    fun possibleWinningStrategies(time:Long, distance:Long): Long{
        return (time-1).downTo(1).map { it * (time - it) }.count { it > distance }.toLong()
    }

    fun parseInput(input: List<String>): List<Pair<Long,Long>>{
        val times = input.first().split(":").last().split(" ").map { it.trim() }.filterNot { it.isEmpty() }.map{it.toLong()}
        val distances = input.last().split(":").last().split(" ").map {it.trim()}.filterNot { it.isEmpty() }.map { it.toLong() }
        val ret = mutableListOf<Pair<Long,Long>>()
        times.forEachIndexed{ idx, it ->
            ret.add(Pair(it, distances[idx]))
        }
        return ret
    }

    fun part1(input: List<Pair<Long,Long>>): Long {
       return input.map {
            possibleWinningStrategies(it.first, it.second)
        }.reduce { acc, i ->  acc * i}.toLong()
    }

    fun part2(input: List<Pair<Long,Long>>): Long {
        val time = input.map { it.first.toString() }.reduce { acc, s -> acc + s }.toLong()
        val distance = input.map { it.second.toString() }.reduce { acc, s -> acc + s }.toLong()
        return possibleWinningStrategies(time, distance)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = parseInput(readInput("Day06_test"))
    check(part1(testInput) == 288L)
    measureTimeMillisPrint {
        val input = parseInput(readInput("Day06"))

        println(part1(input))
        println(part2(input))
    }
}
