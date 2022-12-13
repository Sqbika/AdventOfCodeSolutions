package years.y2021.kt.day12

import common.Solution
import java.util.*

class Day12: Solution() {

    override fun part1(input: List<String>): String = calculatePaths(input, false).toString()

    override fun part2(input: List<String>): String = calculatePaths(input, true).toString()

    fun calculatePaths(input: List<String>, part2:Boolean = false): Int {

        val paths = mutableMapOf<String, Cave>()

        input.map{
            it.split("-")
        }.forEach {
            for ((a,b) in listOf(Pair(0,1), Pair(1,0))) {
                if (paths.containsKey(it[a])) {
                    if (!paths[it[a]]!!.paths.contains(it[b])) paths[it[a]]!!.paths.add(it[b])
                } else {
                    paths[it[a]] = Cave(
                        it[a],
                        mutableListOf(it[b])
                    )
                }
            }
        }

        fun groupByCount(stuff: List<String>) = stuff.groupingBy { it }.eachCount()

        val usedPaths = mutableListOf<String>()

        val pathPermute: Queue<MutableList<String>> = LinkedList(listOf(mutableListOf("start")))

        while(pathPermute.isNotEmpty()) {
            val curPath = pathPermute.poll()

            if (curPath.last() == "end") {
                val pathString = curPath.joinToString(",")

                if (usedPaths.contains(pathString)) continue

                if (!part2 && groupByCount(curPath.filter{it == it.toLowerCase()}).any { it.value > 1 }) continue

                usedPaths.add(pathString)
            } else {
                val nextPaths = paths[curPath.last()]!!.paths

                nextPaths.forEach { it: String ->
                    if (
                        !curPath.contains(it) ||
                        it == it.toUpperCase() ||
                        part2 && it != "start" && it != "end" && groupByCount(curPath.filter{it == it.toLowerCase()}).all{it.value < 2}) {
                        pathPermute.add(curPath.toMutableList().apply { add(it) })
                    }
                }
            }
        }

        return usedPaths.size
    }
}

class Cave(
    val id: String,
    val paths: MutableList<String> = mutableListOf()
) {

    val isSmallCave = id == id.toUpperCase()
}
