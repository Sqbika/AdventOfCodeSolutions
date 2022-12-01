package y2022.kt.day1

import Solution

class Day1: Solution() {

    override fun part1(input: List<String>): String {
        return input.fold(mutableListOf<Int>(0)) { acc, curr ->
            if (curr == "")
                acc.add(0)
            else
                acc[acc.size-1] += curr.toInt()

            acc;
        }.maxOf { it }.toString()
    }

    override fun part2(input: List<String>): String {
        return input.fold(mutableListOf<Int>(0)) { acc, curr ->
            if (curr == "")
                acc.add(0)
            else
                acc[acc.size-1] += curr.toInt()

            acc;
        }.sortedDescending().subList(0,3).sum().toString()
    }

}
