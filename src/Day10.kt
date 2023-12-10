fun main() {
    fun Pair<Int, Int>.add(other: Pair<Int, Int>): Pair<Int, Int> =
        Pair(this.first + other.first, this.second + other.second)

    // Vertical pipes' first element is the direction that flow is going from so that the pipe is going upwards, the second is downwards
    val pipes = mapOf(
        '|' to listOf(Pair(0, 1), Pair(0, -1)),
        'L' to listOf(Pair(1, 0), Pair(0, -1)),
        'J' to listOf(Pair(-1, 0), Pair(0, -1)),
        '7' to listOf(Pair(0, 1), Pair(-1, 0)),
        'F' to listOf(Pair(0, 1), Pair(1, 0)),
        '-' to listOf(Pair(-1, 0), Pair(1, 0)),
        '.' to emptyList(),
        'S' to listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0))
    )

    val verticalPipes = setOf('|', 'L', 'J', '7', 'F')

    fun List<String>.charAt(position: Pair<Int, Int>): Char =
        this[position.second][position.first]

    fun List<String>.startPosition(): Pair<Int, Int> {
        this.mapIndexed() { y, line ->
            line.mapIndexed() { x, char ->
                if (char == 'S') return Pair(x, y)
            }
        }
        error("Start position not found")
    }

    fun getFirstPipe(input: List<String>, startPosition: Pair<Int, Int>): Pair<Int, Int> {
        pipes['S']?.forEach { direction ->
            val position = startPosition.add(direction)
            if (pipes[input.charAt(position)]!!.any { input.charAt(position.add(it)) == 'S' })
                return position
        }
        error("First pipe not found")
    }

    fun solveLoop(input: List<String>): Int {
        var last = input.startPosition()
        var current = getFirstPipe(input,last)
        var counter = 0

        while (input.charAt(current) != 'S') {
            val newCurrent = current.add(pipes[input.charAt(current)]!!.first { current.add(it) != last })
            last = current
            current = newCurrent
            counter++
        }

        return counter
    }

    fun loopMap(input:List<String>): List<List<Char>> {
        val directionsMap = List(input.size) { MutableList(input[0].length) { '.' } }

        fun updateDirectionsMap(position: Pair<Int, Int>, char: Char) {
            directionsMap[position.second][position.first] = char
        }

        var last = input.startPosition()
        var current = getFirstPipe(input, last)
        var currentChar = input.charAt(current)

        while (currentChar != 'S') {
            updateDirectionsMap(
                current,
                if (verticalPipes.contains(currentChar)) {
                    if (last == current.add(pipes[currentChar]!![0])) 'U' else 'D'
                } else 'O'
            )

            val newCurrent = current.add(pipes[currentChar]!!.first { current.add(it) != last })
            last = current
            current = newCurrent
            currentChar = input.charAt(current)
        }

        when (last) {
            current.add(pipes['S']!![0]) -> updateDirectionsMap(current, 'U')
            current.add(pipes['S']!![1]) -> updateDirectionsMap(current, 'D')
            else -> updateDirectionsMap(current, 'O')
        }

        return directionsMap
    }

    fun getStartEndCountCharacters(directionsMap: List<List<Char>>): List<Char> {

        return directionsMap.flatMap{ line ->
            line.filter { it != '.' }.flatMap { listOf(it, if(it == 'U') 'D' else 'U') }
            }
    }

    fun countTilesEnclosedByLoop(input: List<String>): Int {
        val directionsMap = loopMap(input)
        val (startChar, endChar) = getStartEndCountCharacters(directionsMap)
        var counter = 0

        directionsMap.forEach { line ->
            var shouldCount = false

            line.forEach { character ->
                if (character == startChar) shouldCount = true
                else if (character == endChar) shouldCount = false
                else if (shouldCount && character == '.') counter++
            }
        }

        return counter
    }

    fun part1(input: List<String>): Int = (solveLoop(input) + 1) / 2
    fun part2(input: List<String>): Int = countTilesEnclosedByLoop(input)



    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day10_test").parseInput()
 //   check(part1(testInput) == 0)
    measureTimeMillisPrint {
        val input = readInput("Day10")

        println(part1(input))
        println(part2(input))
    }
}
