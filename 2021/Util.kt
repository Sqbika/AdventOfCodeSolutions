object Util {

}

fun <T> List<List<T>>.transpose() = List(this.size) { idx ->
    this.map { it[idx] }
}

fun <T> MutableList<T>.swap(idx1: Int, idx2: Int): MutableList<T> {
    this[idx1] = this[idx2].also {
        this[idx2] = this[idx1]
    }

    return this
}
