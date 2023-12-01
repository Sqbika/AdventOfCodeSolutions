package years.y2022.kt.day3

import common.Solution

class Day3 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "157" to "70"
        )

    val prios = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    override fun part1(input: List<String>): String {
        return input.fold(0) { acc, line ->
            var toAdd = 0
            val parts = line.chunked(line.length/2)

            parts[0]
                .filter { parts[1].contains(it)}
                .toSet()
                .forEach {
                    toAdd += prios.indexOf(it) + 1
                }

            acc + toAdd
        }.toString()
    }

    override fun part2(input: List<String>): String {
        return input.chunked(3).fold(0) { acc, chunk ->
            acc + chunk.fold(prios.toMutableList()) { acc, sack ->
                //println("${acc} - ${sack}")
                acc.retainAll(sack.toList())
                acc
            }.sumOf { char -> prios.indexOf(char) + 1 }
        }.toString()
    }
}
