package common

import kotlin.math.pow

fun <T> T.debugPrint(prefix: String = "", suffix: String = "") = also { println("$prefix$this$suffix") }

fun <T> List<List<T>>.transpose() = List(this.maxOf {it.size}) { idx ->
    this.map { it.getOrNull(idx) }
}

fun List<String>.transposeStrings() = List(this[0].length) { idx -> this.map { it[idx] }.joinToString("") }

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

inline fun <T> Iterable<T>.productOfLong(selector: (T) -> Long): Long = fold(1) {acc, it -> acc * selector(it) }
inline fun <T> Iterable<T>.productOf(selector: (T) -> Int): Int = fold(1) {acc, it -> acc * selector(it) }

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

fun Int.pow(exponential: Int) = toDouble().pow(exponential).toInt()

fun Long.gcm(otherP: Long): Long {
    var result = this
    var other = otherP

    while (other > 0) {
        other = result % other.also {
            result = other
        }
    }

    return result
}

fun List<Long>.gcm(): Long = reduce {acc, i ->  acc.gcm(i)}

fun Long.lcm(other: Long) = this * (other / this.gcm(other))

fun List<Long>.lcm() = reduce {acc, i -> acc.lcm(i) }

fun Pair<Int, Int>.plus(left: Int = 0, right: Int = 0) = Pair(this.first + left, this.second + right)
fun Pair<Int, Int>.minus(left: Int = 0, right: Int = 0) = Pair(this.first - left, this.second - right)

fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = Pair(this.first - other.first, this.second - other.second)

fun List<String>.addToAll(other: List<String>): Sequence<String> = this.asSequence().map { other.map { otherStr -> it + otherStr } }.flatten()

fun IntRange.lazyCount() = this.last - this.first + 1

fun <T> List<T>.repeat(amount: Int) = List(amount) {this}.flatten()

fun listMatch(of: List<Int>, comparedTo: List<Int>, allowPartial: Boolean = true): Boolean {
    if (comparedTo.size < of.size) return false

    if (!allowPartial && of.size != comparedTo.size) return false

    for (idx in of.indices) {
        if (allowPartial && idx == of.size-1 && of[idx] < comparedTo[idx]) {
            return true
        }

        if (of[idx] != comparedTo[idx]) return false
    }

    return true
}
fun String.stringDiff(other: String): Int = this.zip(other).count { (left, right) -> left != right }

inline fun <T> List<T>.iterateVarientsWithoutOne() = List(this.size) { idx -> this.toMutableList().run { removeAt(idx); toList()} }
