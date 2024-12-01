package years.y2024.kt.day1

import common.Solution
import kotlin.math.abs

class Day1 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "11" to "31"
        )

    override fun part1(input: List<String>): String {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        input.forEach { it ->
            val parts = it.split("   ").map { it.toInt() }
            left.add(parts[0])
            right.add(parts[1])
        }

        return left.sorted().zip(right.sorted()).sumOf { abs(it.first - it.second) }.toString()
    }

    override fun part2(input: List<String>): String {
        val scoreCache = mutableMapOf<Int, Int>()
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        input.forEach { it ->
            val parts = it.split("   ").map { it.toInt() }
            left.add(parts[0])
            right.add(parts[1])
        }

        return left.sumOf { num ->
            if (!scoreCache.containsKey(num)) {
                scoreCache[num] = right.count { it == num }
            }

            num * scoreCache[num]!!
        }.toString()
    }
}
