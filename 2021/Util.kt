object Util {

}

fun <T> List<List<T>>.transpose() = List(this.size) { idx ->
    this.map { it[idx] }
}
