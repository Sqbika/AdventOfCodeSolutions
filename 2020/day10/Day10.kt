package day10

import Solution
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.pow
import kotlin.test.assertEquals

class Day10 (path:String) : Solution(path) {

    override fun part1() {
        println("Part1 result: ${solvePart1(input.map{it.toInt()}.sorted())}")
    }

    override fun part2() {
        val intInput = input.map{it.toInt()}.sorted()
        println("Part 2 result: ${solvePart2(listOf(listOf(0), intInput, listOf(intInput.last() + 3)).flatten())}")
    }

    override fun test() {
        val intInput = test.map {it.toInt()}.sorted()
        val result = solvePart1(intInput)
        println("Part 1 Test Result: $result")
        //assertEquals(220, result)
        val part2Result = solvePart2(listOf(listOf(0), intInput, listOf(intInput.last() + 3)).flatten())
        println("Part 2 Test Result: $part2Result")
    }

    private fun solvePart1(input:List<Int>):Int {
        return listOf(listOf(0), input, listOf(input.last() + 3))
                .flatten()
                .zip(listOf(input, listOf(input.last() + 3)).flatten())
                .map { it.second - it.first}
                .groupingBy { it }
                .eachCount()
                .values
                .reduce(Int::times)
    }

    private fun solvePart2(input:List<Int>): String {
        return input.map { num -> input.filter { num + 3 >= it && it > num}}.fold(1) {
            acc: Int, list: List<Int> ->
            acc * max(list.size, 1)
        }.toString()
    }

    private fun solvePart2Bad1(input:List<Int>):Int {
        val theMap = input.zip(input.map { num -> input.filter { num + 3 >= it && it > num}}).toMap()
        var result:List<List<Int>?> = listOf(listOf(0))
        var count = 0
        while(result.isNotEmpty()) {
            val mappedList = result.map { arrs ->
                arrs!!.map {
                    theMap[it]
                }
            }
            count += mappedList.count {it.isEmpty()}
            result = mappedList.flatten()
        }
        return count
    }

}
