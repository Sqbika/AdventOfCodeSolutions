package years.y2022.kt.day4

import common.Solution

class Day4 : Solution() {
    override val tests: List<Pair<String, String>>
        get() = listOf(
            "2" to "4"
        )

    override fun part1(input: List<String>): String {
        return input.fold(0) { acc, line ->
            val parts = line.split(",")

            val elfOne = parts[0].split("-").map { it.toInt()}
            val elfTwo = parts[1].split("-").map { it.toInt()}

            if (
                elfOne[0] <= elfTwo[0] && elfTwo[1] <= elfOne[1] ||
                elfTwo[0] <= elfOne[0] && elfOne[1] <= elfTwo[1]
            )
                acc + 1
            else
                acc
        }.toString()
    }

    override fun part2(input: List<String>): String {
        return input.fold(0) { acc, line ->
            val parts = line.split(",")

            val elfOne = parts[0].split("-").map { it.toInt()}
            val elfTwo = parts[1].split("-").map { it.toInt()}

            if (
                elfOne[0] <= elfTwo[0] && elfTwo[0] <= elfOne[1] ||
                elfTwo[0] <= elfOne[0] && elfOne[0] <= elfTwo[1] ||
                elfTwo[1] <= elfOne[1] && elfOne[1] <= elfTwo[0] ||
                elfOne[1] <= elfTwo[1] && elfTwo[1] <= elfOne[0]
            )
                acc + 1
            else
                acc
        }.toString()
    }
}
