package day11

import Solution
import kotlin.math.max
import kotlin.math.min
import kotlin.test.assertEquals

class Day11(path:String) : Solution(path) {

    private val SEAT = '#'

    override fun part1() {
        println("Part1 Solution: ${solvePart1(input)}")
    }

    override fun part2() {
        println("Part1 Solution: ${solvePart2(input)}")
    }

    override fun test() {
        assertEquals(8, countAdjacentOccpy(listOf("###", "#.#", "###"), 1,1))
        assertEquals(2, countAdjacentOccpy(listOf("###", "#.#",), 0,0))
        var result = solvePart1(test)
        println("Test1 result: $result")
        assertEquals(37, result)
        var result2 = solvePart2(test)
        println("Test2 result: $result2")
        assertEquals(26, result2)

    }

    //Region Part1
    private fun solvePart1(input: List<String>):Int {
        var previous = input
        var current = input
        while (true) {
            current = iterateSeating(previous)
            if (current != previous) {
                previous = current
            } else {
                break
            }
        }
        return countOccupied(previous)
    }

    private fun iterateSeating(input:List<String>):List<String> {
        return input.mapIndexed { x, strg ->
            strg.mapIndexed {  y, char ->
                if (char == '.')
                    '.'
                else {
                    val count = countAdjacentOccpy(input, x, y)
                    if (char == 'L' && count == 0) '#'
                    else if (char == '#' && count >= 4) 'L'
                    else char
                }
            }.joinToString("")
        }
    }

    private fun countAdjacentOccpy(input: List<String>, x: Int, y:Int ):Int {
        val isXyOccupied = if (input[x][y] == '#') 1 else 0
        return input
                .subList(max(x-1, 0), min(x+2, input.size)) //+2 cause exclusive...
                .map { seatString -> seatString.substring(max(y-1, 0)..min(y+1, seatString.length-1)).count { it == SEAT }}
                .sum() - isXyOccupied
    }

    private fun countOccupied(input: List<String>):Int {
        return input.map {
            it.count {char -> char == '#'}
        }.sum()
    }

    //endregion

    private val iterations = listOf(
            listOf(-1, 0), listOf(-1, -1), listOf(0,-1), listOf(1, -1), listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(-1, 1)
    )

    //region Part2
    private fun solvePart2(input: List<String>): Int {
        var previous = input
        var current = input
        while (true) {
            current = iteratePart2Seating(previous)
            if (current != previous) {
                previous = current
            } else {
                break
            }
        }
        return countOccupied(previous)
    }

    private fun iteratePart2Seating(input:List<String>):List<String> {
        return input.mapIndexed { x, strg ->
            strg.mapIndexed {  y, char ->
                if (char == '.')
                    '.'
                else {
                    val count = countRayTracedOccSeats(input, x, y)
                    if (char == 'L' && count == 0) '#'
                    else if (char == '#' && count >= 5) 'L'
                    else char
                }
            }.joinToString("")
        }
    }

    private fun countRayTracedOccSeats(input: List<String>, x:Int, y:Int):Int {
        var result = 0
        iterations.forEach {iter ->
            var cnt = 1
            do {
                val dX = x + (iter[0] * cnt)
                val dY = y + (iter[1] * cnt)
                if (dX < 0 || dX > input.size-1 || dY < 0) break;
                val line = input[dX]
                if (dY > line.length-1) break;
                when (line[dY]) {
                    '#' -> {
                        result++;
                        break;
                    }
                    'L' -> break;
                }
                cnt++
            } while(true)
        }
        return result
    }
    //endregion
}
