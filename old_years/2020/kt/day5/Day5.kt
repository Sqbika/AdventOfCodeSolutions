package day5

import common.Solution

fun main(args : Array<String>) {
    val solution = kt.day5.Day5()
    solution.part1()
    println("Part1 done")
    solution.part2()
    println("Part2 done")
}

class Day5 : Solution("kt/day5/") {

    private fun upperDivide(it:Int, with:Int):Int = (it + with - 1) / with

    private fun calcRow(input:String):Int {
        var low = 0;
        var high = 127;
        input.substring(0, 7).forEach {
            when (it) {
                'F' -> high -= upperDivide(high - low, 2)
                'B' -> low += upperDivide(high - low, 2)
            }
        }
        return if (input[6] == 'F') low else high
    }

    private fun calcCol(input:String):Int {
        var low = 0;
        var high = 7;
        input.substring(7).forEach {
            when (it) {
                'L' -> high -= upperDivide(high - low, 2)
                'R' -> low += upperDivide(high - low, 2)
            }
        }
        return if (input[9] == 'L') low else high
    }

    override fun part1() {
        println(input.map {
            calcRow(it) * 8 + calcCol(it)
        }.maxOrNull())
    }

    override fun part2() {
        val ids = input.map {
            calcRow(it) * 8 + calcCol(it)
        }.sorted()
        println("$ids")
        println(ids.find {
            val idx = ids.indexOf(it)
            if (idx+1 < ids.size) {
                ids[idx + 1] == it + 2
            } else false
        }?.plus(1))
    }

}
