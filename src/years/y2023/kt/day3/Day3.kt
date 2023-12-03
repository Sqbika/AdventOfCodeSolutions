package years.y2023.kt.day3

import common.Solution
import common.productOf
import common.threeByThreeGrid
import common.threeByThreeIdx

class Day3 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "4361" to "467835"
        )

    val numberMatcher = Regex("""[0-9]*""")
    val charMatcher = Regex("""[^.0-9]""")
    override fun part1(input: List<String>): String {
        var partSum = 0
        val lineLenth = input[0].length + 1

        val schematic = input.joinToString(".")
        numberMatcher.findAll(schematic).forEach {
            if (it.range.any { idx ->
                schematic.threeByThreeGrid(idx, lineLenth).any { charMatcher.matches("$it") }
            }) {
                if (isTest) println("Adding: ${it.value}")
                partSum += it.value.toInt()
            }
        }

        return partSum.toString()
    }

    override fun part2(input: List<String>): String {
        val lineLenth = input[0].length + 1

        val schematic = input.joinToString(".")
        val numbers = numberMatcher.findAll(schematic)

        return Regex("""[*]""").findAll(schematic).mapNotNull {
            val matchNumbers = numbers.filter { numberMatch -> threeByThreeIdx(it.range.first, lineLenth).any { gearIdx -> numberMatch.range.contains(gearIdx) }}.map { it.value.toInt() }.toList()

            if (matchNumbers.size > 1) {
                return@mapNotNull matchNumbers.productOf()
            } else {
                return@mapNotNull null
            }
        }.sum().toString()
    }
}