package y2021.kt.day5

import Solution

class Day5: Solution() {

    override fun part1(input: List<String>): String = solveMap(input).values.count { it >= 2 }.toString()

    override fun part2(input: List<String>): String = solveMap(input, true).values.count{it >= 2}.toString()

    private fun getRange(from: Int, to: Int) :IntRange {
        return if (from < to) {
            from..to
        } else {
            to..from
        }
    }

    private fun solveMap(input: List<String>, part2: Boolean = false): MutableMap<Pair<Int, Int>, Int> {

        val ventMap: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

        input.forEach { line ->
            val (from, to) = line.split(" -> ").map {it.split(",").map{it.toInt()}}
            if (from[0] == to[0] || from[1] == to[1]) {
                for (i in getRange(from[0],to[0])) {
                    for (j in getRange(from[1],to[1])) {
                        val pair = Pair(i, j)
                        if (ventMap.containsKey(pair)) {
                            ventMap[pair] = ventMap[pair]!! + 1
                        } else {
                            ventMap[pair] = 1
                        }
                    }
                }
            } else if (part2) {
                generateDiagonalSequence(from, to).forEach { pair ->
                    if (ventMap.containsKey(pair)) {
                        ventMap[pair] = ventMap[pair]!! + 1
                    } else {
                        ventMap[pair] = 1
                    }
                }
            }
        }

        return ventMap
    }

    fun generateDiagonalSequence(from: List<Int>, to: List<Int>): List<Pair<Int, Int>> {
        val toReturn = mutableListOf<Pair<Int,Int>>()

        var xRange = getRange(from[0], to[0]).toList()
        if (from[0] > to[0]) xRange = xRange.reversed()
        var yRange = getRange(from[1], to[1]).toList()
        if (from[1] > to[1]) yRange = yRange.reversed()

        return xRange.zip(yRange)
    }

    fun visualise(map: MutableMap<Pair<Int, Int>, Int>) {
        println(
            List(10) { i ->
                List(10) { j ->
                    if (map.containsKey(Pair(j,i))) {
                        map[Pair(j,i)]
                    } else {
                        '.'
                    }
                }.joinToString("")
            }.joinToString("\n")
        )
    }
}
