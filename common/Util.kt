fun <T> List<List<T>>.transpose() = List(this.size) { idx ->
    this.map { it[idx] }
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


inline fun Number.toDigits(): List<Int> = this.toString().toCharArray().map{it.toString().toInt()}
