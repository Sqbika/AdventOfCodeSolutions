package years.y2021.kt.day9

import common.Solution
import kotlin.math.max
import kotlin.math.min

class Day9: Solution() {

    fun findBasins(points: List<List<Int>>): MutableList<Triple<Int, Int, Int>> {
        val lowPoints = mutableListOf<Triple<Int, Int, Int>>()

        for (i in points.indices) {
            for (j in points[i].indices) {
                val num = points[i][j]
                val numbers = points
                    .subList(max(i - 1, 0), min(i + 2, points.size))
                    .map {
                        it.subList(max(j - 1, 0), min(j + 2, points[i].size))
                    }.flatten()

                if (numbers.count { it == num } == 1 && numbers.all { it >= num }) lowPoints.add(
                    Triple(i, j, num)
                )
            }
        }

        return lowPoints
    }

    override fun part1(input: List<String>): String {
        val points = input.map { it.toCharArray().map { it.digitToInt() } }

        return findBasins(points).map { it.third }.sumOf { it + 1 }.toString()
    }

    override fun part2(input: List<String>): String {
        val points = input.map { it.toCharArray().map { it.digitToInt() } }

        val counts = findBasins(points)
            .map {
                adjacentNonNines(
                    points.map { it.map { it.toString() }.toMutableList() }.toMutableList(),
                    it
                ).size + 1
            }

        return counts
            .sorted()
            .takeLast(3)
            .reduce(Int::times)
            .toString()
    }

    private fun adjacentNonNines(points: MutableList<MutableList<String>>, start: Triple<Int, Int, Int>): List<Int> {

        val result: MutableList<Int> = mutableListOf()

        val newProcess = mutableListOf<Triple<Int, Int, Int>>()

        points[start.first][start.second] = "X"

        for (i in max(start.first - 1, 0) until min(start.first + 2, points.size)) {
            val j = start.second
            val point = points[i][j]
            if (point != "9" && point != "X" && !(i == start.first && j == start.second)) {
                result.add(point.toInt())
                points[i][j] = "X"
                newProcess.add(Triple(i, j, point.toInt()))
            }
        }


        for (j in max(start.second - 1, 0) until min(start.second + 2, points[0].size)) {
            val i = start.first
            val point = points[i][j]
            if (point != "9" && point != "X" && !(i == start.first && j == start.second)) {
                result.add(point.toInt())
                points[i][j] = "X"
                newProcess.add(Triple(i, j, point.toInt()))
            }
        }

        newProcess.forEach {
            result.addAll(adjacentNonNines(points, it))
        }

        return result
    }
}
