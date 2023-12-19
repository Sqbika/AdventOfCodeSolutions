package years.y2023.kt.day12

import common.*

class Day12 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "21" to "525152"
        )

    fun countHash(input: String): List<Int> {
        val result = mutableListOf<Int>()
        var counter = 0
        for (char in input) {
            if (char != '#') {
                if (counter > 0) {
                    result.add(counter)
                }
                counter = 0
            } else {
                counter ++
            }
        }
        if (counter > 0) result.add(counter)
        return result
    }

    fun runDay(sequence: List<Int>, map: String): Int {
        val queue = sequence.toMutableList()
        var head = -1

        for (char in map) {
        }

        return 0
    }

    override fun part1(input: List<String>): String =
        input
            .asSequence()
            .map {
                it.split(" ").run {
                    runDay(this[1].split(",").map{it.toInt()}, this[0])
                }
            }
            .sum()
            .toString()

    override fun part2(input: List<String>): String =
        input
            .asSequence()
            .map {
                it.split(" ").run {
                    runDay(this[1].split(",").map{it.toInt()}.repeat(5), "${this[0]}?".repeat(5).dropLast(1))
                }
            }.sum().toString()
}