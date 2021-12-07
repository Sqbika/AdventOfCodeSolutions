package day7

import Solution
import kotlin.math.abs

class Day7(path: String): Solution(path) {

    override fun part1(input: List<String>) {
        val nums = input[0].split(",").map{it.toInt()}

        val groups = nums.groupBy { it }.entries.sortedBy { it.value.size }.toMutableList()

        val range = IntRange(groups.minOf { it.key }, groups.maxOf {it.key})

        val costs = range.map { i ->
            Pair (i, groups.sumOf {
                abs(i - it.key) * it.value.size
            })
        }.sortedBy { it.second }

        println("Part 1: ${costs.first().second}")
    }

    override fun part2(input: List<String>) {
        val nums = input[0].split(",").map{it.toInt()}

        val groups = nums.groupBy { it }.entries.sortedBy { it.value.size }.toMutableList()

        val range = IntRange(groups.minOf { it.key }, groups.maxOf {it.key})

        val costs = range.map { i ->
            Pair (i, groups.sumOf {
                somethingsomethingtrianglenumber(abs(i - it.key)) * it.value.size
            })
        }.sortedBy { it.second }

        println("Part 2: ${costs.first().second}")
    }

    fun somethingsomethingtrianglenumber(n: Int): Int =  (n*n + n) / 2
}
