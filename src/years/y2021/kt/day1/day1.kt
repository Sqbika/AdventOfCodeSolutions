package years.y2021.kt.day1

import common.Solution

class Day1: Solution() {

    override fun part1(input: List<String>): String {
        return input.fold(Pair(0, input.first().toInt())) { acc, s -> Pair(acc.first + (if (acc.second < s.toInt()) 1 else 0), s.toInt())}.toString()
    }

    override fun part2(input: List<String>): String {
        return input
            .zip(
                input.drop(1).zip(
                    input.drop(2)
                ).map { it.first.toInt() + it.second.toInt() }
            ).map {
                it.first.toInt() + it.second
            }.fold(Pair(-1, 0)) { acc, s ->
                Pair(acc.first + if (acc.second < s) 1 else 0, s)
            }.first
        .toString()
    }
}
