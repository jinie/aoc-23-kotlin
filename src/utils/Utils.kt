package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("resources", "$name.txt").readLines()

fun readInputString(name: String) = File("resources", "$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16).padStart(32, '0')


/**
 * Measures execution time in ms, and calls loggingFunction with the result
 * Stolen from https://proandroiddev.com/measuring-execution-times-in-kotlin-460a0285e5ea
 */
inline fun <T> measureTimeMillis(
    loggingFunction: (Long) -> Unit, function: () -> T
): T {

    val startTime = System.currentTimeMillis()
    val result: T = function.invoke()
    loggingFunction.invoke(System.currentTimeMillis() - startTime)

    return result
}

/**
 *  Measures execution time in milliseconds and prints it
 */
inline fun <T> measureTimeMillisPrint(
    function: () -> T
): T {
    return measureTimeMillis({ println("Time Taken $it ms") }) {
        function.invoke()
    }
}


/**
 * Finds up/down/left/right neighbours in a grid
 */
fun neighbours(input: List<List<Int>>, rowIdx: Int, colIdx: Int): List<Pair<Int, Int>> {
    return arrayOf((-1 to 0), (1 to 0), (0 to -1), (0 to 1)).map { (dx, dy) -> rowIdx + dx to colIdx + dy }
        .filter { (x, y) -> x in input.indices && y in input.first().indices }
}

/**
 * Find all neighbours in a grid
 */
fun allNeighbours(values: List<List<Int>>, y: Int, x: Int): List<Pair<Int, Int>> {
    return (-1..1).flatMap { dy -> (-1..1).map { dx -> dy to dx } }
        .filterNot { (dy, dx) -> dy == 0 && dx == 0 }
        .map { (dy, dx) -> y + dy to x + dx }
        .filter { (y, x) -> y in values.indices && x in values.first().indices }
}

fun IntRange.contains(b: IntRange): Boolean {
    return this.first <= b.first && b.last <= this.last
}

fun IntRange.overlaps(b: IntRange): Boolean {
    return this.contains(b.first) || this.contains(b.last) || b.contains(this.first)
}

infix fun IntRange.intersects(other: IntRange): Boolean =
    first <= other.last && last >= other.first

infix fun IntRange.intersect(other: IntRange): IntRange =
    maxOf(first, other.first)..minOf(last, other.last)

fun IntRange.size(): Int =
    last - first + 1

fun bSearch(lower: Long, upper: Long, reverse: Boolean = false, eval: (Long) -> Boolean): Long? {
    var begin = lower
    var end = upper
    var res: Long? = null
    while (begin <= end) {
        val mid = (begin + end) / 2L
        if (eval(mid)) {
            res = mid
            if (reverse) end = mid - 1 else begin = mid + 1
        } else
            if (reverse) begin = mid + 1 else end = mid - 1
    }
    return res
}

fun findLCM(a: Long, b: Long): Long {
    val larger:Long = if (a > b) a else b
    val maxLcm:Long = a * b
    var lcm:Long = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        result = findLCM(result, numbers[i])
    }
    return result
}

fun calculateGCD(a: Int, b: Int): Int {
    var num1 = a
    var num2 = b
    while (num2 != 0) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }
    return num1
}

fun calculateGCDForListOfNumbers(numbers: List<Int>): Int {
    require(numbers.isNotEmpty()) { "List must not be empty" }
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        var num1 = result
        var num2 = numbers[i]
        while (num2 != 0) {
            val temp = num2
            num2 = num1 % num2
            num1 = temp
        }
        result = num1
    }
    return result
}

fun <E> List<List<E>>.transpose(): List<List<E>> =
    List(this[0].size) { i ->
        List(this.size) { j ->
            this[j][i]
        }
    }

fun <E> combinations(list: List<E>, length: Int? = null): Sequence<List<E>> {
    return sequence {
        val n = list.size
        val r = length ?: list.size
        val counters = Array(r) { it }
        val maxes = Array(r) { it + n - r }

        yield(counters.map { list[it] })
        while (true) {
            val lastNotAtMax = counters.indices.findLast { counter ->
                counters[counter] != maxes[counter]
            } ?: return@sequence // All was at max

            counters[lastNotAtMax]++

            // Increase the others behind (that were one max)
            for (toUpdate in lastNotAtMax + 1 until r) {
                counters[toUpdate] = counters[toUpdate - 1] + 1
            }
            yield(counters.map { list[it] })
        }
    }
}

@JvmName("combinationsExt")
fun <E> List<E>.combinations(length: Int? = null) = combinations(this, length)
