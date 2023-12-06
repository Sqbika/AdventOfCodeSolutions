package years.y2023.kt.day6

import common.Solution
import common.debugPrint
import common.productOf

class Day6 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "288" to ""
        )

    override fun part1(input: List<String>): String = input.map {
        it.split(" ").filter { it.isNotBlank() }.drop(1).map { it.toInt() }
    }.let {
        it[0].zip(it[1])
    }.productOf { (time, distance) ->
        println("Time: $time | Distance: $distance")
        IntRange(0, time).filter {
            (time-it) * it > distance
        }.debugPrint("[Size] -> ").size
    }.toString()

    override fun part2(input: List<String>): String = input.map {
        it.split(" ", limit=2)[1].replace(" ", "").toLong()
    }.let {
        val time = it[0]
        val distance = it[1]

        LongRange(0, time).filter {
            (time-it) * it > distance
        }.size.toString()
    }
}