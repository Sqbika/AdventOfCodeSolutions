package years.y2024.kt.day3

import common.Solution

class Day3 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "161" to "",
            "" to "48"
        )

    val mulRegex = Regex("""mul\(([0-9]+),([0-9]+)\)""")

    override fun part1(input: List<String>): String = mulRegex.findAll(input.joinToString("\n")).sumOf {
        it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt()
    }.toString()

    val p2Regex = Regex("""mul\(([0-9]+),([0-9]+)\)|do\(\)|don't\(\)""")

    override fun part2(input: List<String>): String {
        val parts = p2Regex.findAll(input.joinToString("\n"))

        var enabled = true
        var sum = 0
        parts.forEach {
            if (it.value == "do()") {
                enabled = true
            } else if (it.value == "don't()") {
                enabled = false
            } else if (enabled) {
                sum += it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt()
            }
        }

        return sum.toString()
    }
}