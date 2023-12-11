package years.y2023.kt.day11

import common.Solution
import common.debugPrint
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day11 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "374" to "8410"
        )

    fun amountInListBetweenLeftAndRight(left: Int, right: Int, inside: List<Int>): Int = inside.filter { min(left, right) < it && it < max(left, right) }.size

    override fun part1(input: List<String>): String {
        val emptyColIdx = mutableListOf<Int>()
        val emptyRowIdx = mutableListOf<Int>()

        for (x in input[0].indices) {
            if (input.all {it[x] == '.'}) {
                emptyColIdx.add(x)
            }
        }

        for (y in input.indices) {
            if (input[y].all {it == '.'}) {
                emptyRowIdx.add(y)
            }
        }

        val length = input[0].length

        val mapStr = input.joinToString("")
        val pos = Regex("""#""").findAll(mapStr).map {
            it.range.first.run {
                Pair(this / length, this % length)
            }
        }

        return pos.mapIndexed { index, left ->
            pos.drop(index+1).map { right ->
                abs(left.first - right.first) + abs(left.second - right.second) +
                        amountInListBetweenLeftAndRight(left.first, right.first, emptyRowIdx) +
                        amountInListBetweenLeftAndRight(left.second, right.second, emptyColIdx)
            }.toList().debugPrint("[Pos$index] $left -> ")
        }.debugPrint().sumOf { it.sum() }.toString()
    }


    override fun part2(input: List<String>): String {
        val emptyColIdx = mutableListOf<Int>()
        val emptyRowIdx = mutableListOf<Int>()

        for (x in input[0].indices) {
            if (input.all {it[x] == '.'}) {
                emptyColIdx.add(x)
            }
        }

        for (y in input.indices) {
            if (input[y].all {it == '.'}) {
                emptyRowIdx.add(y)
            }
        }

        val length = input[0].length

        val mapStr = input.joinToString("")
        val pos = Regex("""#""").findAll(mapStr).map {
            it.range.first.run {
                Pair(this / length, this % length)
            }
        }

        return pos.mapIndexed { index, left ->
            pos.drop(index+1).map { right ->
                abs(left.first - right.first) + abs(left.second - right.second) + (amountInListBetweenLeftAndRight(left.first, right.first, emptyRowIdx) * 999_999L) + (amountInListBetweenLeftAndRight(left.second, right.second, emptyColIdx) * 999_999L)
            }.toList().debugPrint("[Pos$index] $left -> ")
        }.debugPrint().sumOf { it.sum() }.toString()
    }
}