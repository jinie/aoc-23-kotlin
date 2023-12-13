import utils.findLCM

fun <E> List<List<E>>.transpose(): List<List<E>> =
    List(this[0].size) { i ->
        List(this.size) { j ->
            this[j][i]
        }
    }

fun List<String>.splitEmpty():List<List<String>>{
    val ret: MutableList<List<String>> = mutableListOf()
    var tret: MutableList<String> = mutableListOf()
    var si = 0
    var ei = 0
    for(i in 0 .. this.lastIndex){
        if(this[i].isEmpty()) {
            ret.add(tret)
            tret = mutableListOf()
        }else{
            tret.add(this[i])
        }
    }
    if(tret.isNotEmpty())
        ret.add(tret)
    return ret
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


fun List<Int>.calculateGCD(): Int {
    require(this.isNotEmpty()) { "List must not be empty" }
    var result = this.first()
    for (i in 1 until this.size) {
        var num1 = result
        var num2 = this[i]
        while (num2 != 0) {
            val temp = num2
            num2 = num1 % num2
            num1 = temp
        }
        result = num1
    }
    return result
}

fun List<Long>.calculateGCD(): Long {
    require(this.isNotEmpty()) { "List must not be empty" }
    var result = this.first()
    for (i in 1 until this.size) {
        var num1 = result
        var num2 = this[i]
        while (num2 != 0L) {
            val temp = num2
            num2 = num1 % num2
            num1 = temp
        }
        result = num1
    }
    return result
}

fun List<Long>.findLCM(): Long {
    var result = this.first()
    for (i in 1 until this.size) {
        result = findLCM(result, this[i])
    }
    return result
}