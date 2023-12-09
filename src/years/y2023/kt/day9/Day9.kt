package years.y2023.kt.day9

import common.Solution
import kotlin.math.abs

class Day9 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "114" to "2",
            "-5" to "0"
        )

    override fun part1(input: List<String>): String =
        input.map {
            it.split(" ").map { it.toInt() }
        }.sumOf { list ->
            var head = list
            val reduces = mutableListOf(list)
            while (head.any { it != 0 }) {
                head = head.zipWithNext { a, b -> b - a }
                reduces.add(head)
            }
            reduces.sumOf { it.last() }
        }.toString()

    override fun part2(input: List<String>): String =
        input.map {
            it.split(" ").map { it.toInt() }
        }.sumOf { list ->
            var head = list
            val reduces = mutableListOf(list)
            while (head.any { it != 0 }) {
                head = head.zipWithNext { a, b -> b - a }
                reduces.add(head)
            }
            reduces.map { it.first() }.reversed().fold(0) { acc, it -> it - acc } as Int
        }.toString()
}
