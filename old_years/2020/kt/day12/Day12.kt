package day12

import common.Solution
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin
import kotlin.test.assertEquals

class Day12(path:String) : Solution(path) {

    override fun part1() {
        println("Part1: ${solvePart1(input)}")
    }

    override fun part2() {
        println("Part1: ${solvePart2(input)}")
    }

    override fun test() {
        assertEquals(listOf(4,4) * 2, listOf(8,8))
        val part1Result = solvePart1(test)
        println("Test1: $part1Result")
        //assertEquals(25, part1Result)
        val part2Result = solvePart2(test)
        println("Test2: $part2Result")
        //assertEquals(286, part2Result)

    }

    private val cardinals = listOf(listOf(0, 1), listOf(-1, 0), listOf(0, -1), listOf(1, 0))

    private fun solvePart1(input: List<String>): Int {
        val ops = input.map {Pair(it[0], Integer.parseInt(it.substring(1)))}
        var pos = mutableListOf(0, 0); var dir = 0
        ops.forEach { char ->
            when (char.first) {
                'N' -> {pos[0] += char.second}
                'S' -> {pos[0] -= char.second}
                'E' -> {pos[1] += char.second}
                'W' -> {pos[1] -= char.second}
                'L' -> {dir = (dir - (char.second / 90) + 4) % 4}
                'R' -> {dir = (dir + (char.second / 90) + 4) % 4}
                'F' -> {pos = pos.zip(cardinals[dir] * char.second).map{it.first + it.second}.toMutableList()}
            }
        }
        return pos.map{abs(it)}.sum()
    }

    private fun rotateWaypoint(waypoint:List<Int>, degree:Double): List<Int> = listOf(
            round(waypoint[0] * cos(Math.toRadians(degree)) - waypoint[1] * sin(Math.toRadians(degree))).toInt(),
            round(waypoint[0] * sin(Math.toRadians(degree)) + waypoint[1] * cos(Math.toRadians(degree))).toInt()
    )

    private fun Double.e():Double = (this + 360) % 360

    private fun solvePart2(input: List<String>): Int {
        val ops = input.map {Pair(it[0], Integer.parseInt(it.substring(1)))}
        var lastPos = listOf(0,0)
        var lastWaypoint = listOf(1,10)
        var pos = mutableListOf(0, 0)
        var waypoint = mutableListOf(1, 10)
        ops.forEach { char ->
            lastPos = pos.toList()
            lastWaypoint = waypoint.toList()
            when (char.first) {
                'N' -> {waypoint[0] += char.second}
                'S' -> {waypoint[0] -= char.second}
                'E' -> {waypoint[1] += char.second}
                'W' -> {waypoint[1] -= char.second}
                'L' -> {waypoint = rotateWaypoint(waypoint, (char.second.toDouble()*-1).e()).toMutableList()}
                'R' -> {waypoint = rotateWaypoint(waypoint, char.second.toDouble().e()).toMutableList()}
                'F' -> {pos = pos.zip(waypoint * char.second).map{it.first + it.second}.toMutableList()}
            }
        }
        return pos.map{abs(it)}.sum()
    }

}

private operator fun Iterable<Int>.times(t: Int): List<Int> = this.map{it*t}
