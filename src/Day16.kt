import utils.*
import utils.Direction.*

enum class Element { LeftMirror, RightMirror, HorizontalSplitter, VerticalSplitter }
private class Maze(content: Iterable<Pair<Point2d, Element>>) {

    private val grid: MutableMap<Point2d, Element> = content.associate { it.first to it.second }.toMutableMap()

    val area: Area = Area(grid.keys)
    operator fun get(point: Point2d): Element? = grid[point]
    fun beam(source: Point2d, heading: Direction): Set<Point2d> = buildSet {
        val seen = mutableSetOf<Pair<Point2d, Direction>>()
        fun beamBranch(source: Point2d, heading: Direction) {
            var point = source
            var direction = heading

            while (point in area) {
                if (point to direction in seen) return
                seen.add(point to direction)
                add(point)

                direction = when (get(point)) {
                    null -> direction
                    Element.LeftMirror -> when (direction) {
                        Left -> Up
                        Right -> Down
                        Down -> Right
                        Up -> Left
                    }

                    Element.RightMirror -> when (direction) {
                        Left -> Down
                        Right -> Up
                        Up -> Right
                        Down -> Left
                    }

                    Element.HorizontalSplitter -> when (direction) {
                        Left, Right -> direction
                        Up, Down -> Left.also { beamBranch(point.move(Right), Right) }
                    }

                    Element.VerticalSplitter -> when (direction) {
                        Up, Down -> direction
                        Left, Right -> Up.also { beamBranch(point.move(Down), Down) }
                    }
                }
                point = point.move(direction)
            }
        }
        beamBranch(source, heading)
    }
}

fun parsePoint(ch: Char): Element? = when (ch) {
    '\\' -> Element.LeftMirror
    '/' -> Element.RightMirror
    '|' -> Element.VerticalSplitter
    '-' -> Element.HorizontalSplitter
    '.' -> null
    else -> error("Unknown maze entry: $ch.")
}

private fun parseInput(input: String): Maze {

    return input.lines().asReversed().flatMapIndexed { y, line ->
            line.mapIndexed { x, c ->
                parsePoint(c)?.let {
                    Point2d(
                        x, y
                    ) to it
                }
            }
        }.filterNotNull().let(::Maze)
}


fun part1(input: String): Int {
    val maze = parseInput(input)
    return with(maze.area) { Point2d(xRange.first, yRange.last) }.let { source -> maze.beam(source, Right) }.count()
}

fun part2(input: String): Int {
    val maze = parseInput(input)
    return buildList {
        with(maze.area) {
            for (y in yRange) add(maze.beam(Point2d(xRange.first, y), Right))
            for (x in xRange) add(maze.beam(Point2d(x, yRange.last), Down))
            for (y in yRange) add(maze.beam(Point2d(xRange.last, y), Left))
            for (x in xRange) add(maze.beam(Point2d(x, yRange.first), Up))
        }
    }.maxOf { beam -> beam.count() }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputString("Day16_test")
    check(part1(testInput) == 46)
    measureTimeMillisPrint {
        val input = readInputString("Day16")

        println(part1(input))
        println(part2(input))
    }
}
