package day10

import common.Solution
import java.math.BigInteger

class Day10 (path:String) : Solution(path) {

    override fun part1(input: List<String>) {
        println("Part1 result: ${solvePart1(input.map{it.toInt()}.sorted())}")
    }

    override fun part2(input: List<String>) {
        val intInput = input.map{it.toInt()}.sorted()
        println("Part 2 result: ${solvePart2(listOf(listOf(0), intInput, listOf(intInput.last() + 3)).flatten())}")
    }

    fun test() {
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

    private fun solvePart2(input:List<Int>):BigInteger {
        val max = input.maxOrNull()!!
        val theMap = input.zip(input.map { num -> input.filter { num + 3 >= it && it > num}}).toMap()
        var result: Map<Int,BigInteger> = mapOf(Pair(0,1.toBigInteger()))
        var count = 0.toBigInteger()
        while(result.isNotEmpty()) {
            val resultMap = mutableMapOf<Int,BigInteger>()
            result.forEach { rootNum ->
                theMap[rootNum.key]!!.forEach {
                    if (it == max) {
                        count += rootNum.value
                    } else if (!resultMap.containsKey(it))
                        resultMap[it] = rootNum.value
                    else
                        resultMap[it] = resultMap[it]!! + rootNum.value
                }
            }
            result = resultMap
        }
        return count
    }

}
