fun main() {

    fun parseInput(input: List<String>): List<Pair<Int, List<Map<String, Int>>>> {
        return input.map { line ->
            val (gameId, rounds) = line.split(":")
            val roundsList = rounds.split(";").map { round ->
                round.split(",").map { color ->
                    val (count, color) = color.trim().split(" ")
                    color to count.toInt()
                }.toMap()
            }
            gameId.trim().split(" ").last().toInt() to roundsList
        }
    }

    fun part1(input: List<Pair<Int, List<Map<String, Int>>>>): Int {
        return input.filter { (_, rounds) ->
            rounds.all { round ->
                round.all { (color, count) ->
                    when (color) {
                        "red" -> count <= 12
                        "green" -> count <= 13
                        "blue" -> count <= 14
                        else -> error("Unknown color $color")
                    }
                }
            }
        }.sumOf { (gameId, _) -> gameId }
    }

    fun part2(input: List<Pair<Int, List<Map<String, Int>>>>): Int {
        return input.sumOf { (_, rounds) ->
            val maxPerColor = rounds.flatMap { round ->
                round.map { (color, count) -> color to count }
            }.groupBy { (color, _) -> color }
                .map { (_, values) -> values.maxOf { (_, count) -> count } }
            maxPerColor.reduce { acc, i -> acc * i }
        }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = parseInput(readInput("Day02_test"))
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)
    measureTimeMillisPrint {
        val input = parseInput(readInput("Day02"))

        println(part1(input))
        println(part2(input))
    }


}

                                                                                                                                