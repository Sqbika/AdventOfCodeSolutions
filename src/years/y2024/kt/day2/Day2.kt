package years.y2024.kt.day2

import common.Solution
import common.iterateVarientsWithoutOne

class Day2 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "2" to "4"
        )

    override fun part1(input: List<String>): String = input.sumOf { level -> if (isLevelSafe(level.split(" "))) 1L else 0L }.toString()

    override fun part2(input: List<String>): String = input.sumOf { level ->
        return@sumOf level.split(" ")
            .windowed(2, 1)
            .map { it[0].toInt() - it[1].toInt() }
            .let { diffs ->
                if (diffs.all { it in 1..3 } || diffs.all { it in -1 downTo -3 }) {
                    return@let 1L
                }

                val variants = level.split(" ").iterateVarientsWithoutOne()
                println(variants)

                return@let if (variants.any {isLevelSafe(it)}) 1L else 0L
            }
    }.toString()

    private fun isLevelSafe(input: List<String>): Boolean = input
        .windowed(2, 1)
        .map { it[0].toInt() - it[1].toInt() }
        .let { diffs -> diffs.all { it in 1..3 } || diffs.all { it in -1 downTo -3 } }
}