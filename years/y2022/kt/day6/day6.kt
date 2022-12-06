package y2022.kt.day6

import Solution

class Day6: Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "5" to "",
            "6" to ""
        )

    override fun part1(input: List<String>): String {
        input[0].windowed(4, 1).forEachIndexed { index, str ->
            if (str.toSet().size == str.length) return (index+4).toString()
        }

        throw Exception("failed")
    }

    override fun part2(input: List<String>): String {
        input[0].windowed(14, 1).forEachIndexed { index, str ->
            if (str.toSet().size == str.length) return (index+14).toString()
        }

        throw Exception("failed")
    }
}
