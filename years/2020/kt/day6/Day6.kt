package day6

import Solution

fun main(args : Array<String>) {
    val solution = kt.day6.Day6()
    solution.part1()
    println("Part1 done")
    solution.part2()
    println("Part2 done")
}

class Day6 : Solution("kt/day6/") {

    fun preParse(input:List<String>):List<String> =
            input.joinToString("\n").split("\n\n").map { x -> x.replace("\n".toRegex(), "|") }

    override fun part1() {
        println("P1: " + preParse(input).map { it.toSortedSet().size }.reduce { acc, i -> acc + i })
    }

    override fun part2() {
        var result = 0
        val groups = preParse(input)
        groups.forEach {group ->
            val lines = group.split("|").map { it.toSortedSet() }
            result += lines.flatten().distinct().filter { char -> lines.all { it.contains(char) } }.size
        }

        println("P2: " + result)
    }

}
