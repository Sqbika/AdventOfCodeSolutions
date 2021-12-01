package day1

import Solution

class Day1(path: String) : Solution(path) {

    override fun part1(input: List<String>) {
        println(input.fold(Pair(0, input.first().toInt())) { acc, s -> Pair(acc.first + (if (acc.second < s.toInt()) 1 else 0), s.toInt())})
    }

    override fun part2(input: List<String>) {
        println(input
            .zip(
                input.drop(1).zip(
                    input.drop(2)
                ).map { it.first.toInt() + it.second.toInt() }
            ).map {
                it.first.toInt() + it.second
            }.fold(Pair(-1, 0)) { acc, s ->
                Pair(acc.first + if (acc.second < s) 1 else 0, s)
            }.first)
    }
}
