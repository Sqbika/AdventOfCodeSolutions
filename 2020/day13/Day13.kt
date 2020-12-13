package day13

import Solution
import kotlin.math.ceil
import kotlin.math.round
import kotlin.test.assertEquals

class Day13(path:String) : Solution(path) {

    override fun part1() {
        println("Part1: ${solvePart1(input)}")
    }

    override fun part2() {
        println("Part2 ${solvePart2(input)}")
    }

    override fun test() {
        /*val test1Result = solvePart1(test)
        println("Test1: $test1Result")
        assertEquals(295, test1Result)*/
        val test2Result = solvePart2(test)
        println("Test2: $test2Result")
        //assertEquals(1068788, test2Result)
        testPart2("17,x,13,19", 3417)
        testPart2("67,7,59,61", 754018)
        testPart2("67,x,7,59,61", 779210)
        testPart2("67,7,x,59,61", 1261476)
        testPart2("1789,37,47,1889", 1202161486)
    }

    private fun testPart2(input:String, exp:Long) {
        val result = solvePart2(listOf("", input))
        println("Test [$input] | Exp: [$exp] | Act: [$result] | Success: ${result == exp} | Rems: ${input.split(",").mapIndexed() { acc, it -> if (it == "x") "x" else (it.toLong() - (result % it.toLong())) % it.toLong() == acc.toLong()}.filter { it != "x" }}")
    }

    fun solvePart1(input:List<String>): Int {
        val TOA = input[0].toInt()
        val buses = input[1].split(',').filter {it != "x"}.map {it.toInt()}
        val closeVals = buses.map{Pair(it, ceil((TOA / it).toDouble()+1) * it)}.filter { it.second > TOA}.sortedBy { it.second }
        return round((closeVals.first().second - TOA) * closeVals.first().first).toInt()
    }

    fun solvePart2(input:List<String>): Long {
        //Pair(a,n)
        val buses = input[1].replace("x", "-1").split(',').mapIndexed{ idx, it -> Pair( idx.toLong(),  it.toInt().toLong())}.filter{it.second != -1L}
        var idx = 1
        var mult = buses[0].second
        var curr = 0L
        while (idx < buses.size) {
            curr += mult
            if ((curr + buses[idx].first) % buses[idx].second == 0L) {
                mult *= buses[idx].second
                idx++
            }
        }
        return curr
    }
}
