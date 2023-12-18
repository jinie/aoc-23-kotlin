import utils.Direction
import utils.Point2d
import utils.measureTimeMillisPrint
import utils.readInput
import java.util.*

class Day17 {

    fun findOptimalPath(grid: List<List<Int>>, initialStates: List<State>, minBlocks: Int, maxBlocks: Int): Int {
        val end = Point2d(grid.first().lastIndex, grid.lastIndex)
        val toVisit = PriorityQueue<CompareCost>()
        val costs = mutableMapOf<State, Int>().withDefault { Int.MAX_VALUE }

        initialStates.forEach { state ->
            costs[state] = 0
            toVisit.add(CompareCost(state, 0))
        }

        while (toVisit.isNotEmpty()) {
            val current = toVisit.poll()

            when {
                current.state.point == end -> return current.cost
                else -> {
                    current.state.next(minBlocks, maxBlocks).filter { it.point.y in grid.indices && it.point.x in grid.first().indices }
                    .forEach { next ->
                        val newCost = current.cost + grid[next.point.y][next.point.x]
                        if (newCost < costs.getValue(next)) {
                            costs[next] = newCost
                            toVisit.add(CompareCost(next, newCost))
                        }
                    }
                }
            }
        }
        return -1
    }

    data class State(val point: Point2d, val dir: Point2d, val blocks: Int) {
        fun next(minBlocks: Int, maxBlocks: Int) = buildList {
            if(blocks < minBlocks)
                add(copy(point = point + dir, dir = dir, blocks = blocks + 1))
            else{

                val left = Point2d(dir.y, dir.x)
                val right = Point2d(-dir.y, -dir.x)

                add(copy(point = point + left, dir = left, blocks = 1))
                add(copy(point = point + right, dir = right, blocks = 1))

                if (blocks < maxBlocks) {
                    add(copy(point = point + dir, dir = dir, blocks = blocks + 1))
                }
            }
        }
    }

    private data class CompareCost(val state: State, val cost: Int) : Comparable<CompareCost> {
        override fun compareTo(other: CompareCost): Int {
            return cost compareTo other.cost
        }
    }
}
fun main() {



    fun part1(input: List<List<Int>>): Int {
        return Day17().findOptimalPath(input,listOf(Day17.State(Point2d(0, 0), Point2d.EAST, 0)),0,3)
    }

    fun part2(input: List<List<Int>>): Int {

        return Day17().findOptimalPath(
            input,
            listOf(
                Day17.State(Point2d(0, 0), Point2d.EAST, 0),
                Day17.State(Point2d(0, 0), Point2d.SOUTH, 0)
            ),4,10)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test").map { row -> row.map { it.digitToInt() } }
    check(part1(testInput) == 102)
    //check(part2(testInput) == 145)
    measureTimeMillisPrint {
        val input = readInput("Day17").map { row -> row.map {it.digitToInt()} }
        println(part1(input))
        println(part2(input))
    }
}