package common

fun <T> T.debugPrint() = also { println(this) }

fun <T> List<List<T>>.transpose() = List(this.maxOf {it.size}) { idx ->
    this.map { it.getOrNull(idx) }
}

fun <T> MutableList<T>.swap(idx1: Int, idx2: Int): MutableList<T> {
    this[idx1] = this[idx2].also {
        this[idx2] = this[idx1]
    }

    return this
}

fun <T> MutableList<T>.dropTake(n:Int): MutableList<T> {
    val toReturn = this.take(n)
    repeat(n) { this.removeAt(0) }
    return toReturn.toMutableList()
}

fun <T> MutableList<T>.dropTakeLast(n:Int): MutableList<T> {
    val toReturn = this.takeLast(n)
    repeat(n) { this.removeLast() }
    return toReturn.toMutableList()
}

public inline fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long {
    var sum: Long = 1.toLong()
    for (element in this) {
        sum *= selector(element)
    }
    return sum
}

inline fun Iterable<Int>.productOf() = fold(1) { acc: Int, i: Int -> acc*i }

inline fun Number.toDigits(): List<Int> = this.toString().toCharArray().map{it.toString().toInt()}

inline fun List<Int>.max() = this.maxOf { it }

public inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        if (!predicate(item))
            break
    }
    return list
}

val numbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

inline fun String.numberWordToInt(): Int = numbers.indexOf(this.lowercase()) + 1

inline fun List<String>.threeByThreeGrid(idx: Int, width: Int) = listOf(idx-width-1, idx-width, idx-width+1, idx-1, idx+1, idx+width-1, idx+width, idx+width+1).mapNotNull {getOrNull(it)}

inline fun String.threeByThreeGrid(idx: Int, width: Int) = threeByThreeIdx(idx, width).mapNotNull {getOrNull(it)}

fun threeByThreeIdx(idx: Int, width: Int) = listOf(idx-width-1, idx-width, idx-width+1, idx-1, idx+1, idx+width-1, idx+width, idx+width+1)

