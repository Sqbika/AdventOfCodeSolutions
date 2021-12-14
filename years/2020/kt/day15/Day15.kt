package day15

import Solution

class Day15(path:String) : Solution(path) {

    override fun part1() {
        println("Part1 : ${solve(input,2020)}")
    }

    override fun part2() {
        println("Part2 : ${solve(input,30000000)}")
    }

    override fun test() {
        println("Test1.0 : ${solve(test,2020)} | Corr: 436")
        println("Test1.1 : ${solve(listOf("1,3,2"),2020)} | Corr: 1")
        println("Test1.2 : ${solve(listOf("2,1,3"),2020)} | Corr: 10")
        println("Test1.2 : ${solve(listOf("1,2,3"),2020)} | Corr: 27")
        println("Test1.3 : ${solve(listOf("2,3,1"),2020)} | Corr: 78")
        println("Test1.4 : ${solve(listOf("3,2,1"),2020)} | Corr: 438")
        println("Test1.5 : ${solve(listOf("3,1,2"),2020)} | Corr: 1836")
    }

    private fun solve(input:List<String>, maxIter:Int): Int {
        val resultMap = mutableMapOf<Int, MutableList<Int>>()
        val ints = input[0].split(",").map{it.toInt()}
        ints.forEachIndexed {idx, it -> resultMap[it] = mutableListOf(idx+1) }
        var turn = ints.size-1
        var lastNum = ints.last()
        do {
            turn++
            if (!resultMap.containsKey(lastNum)) {
                lastNum = 0
                resultMap[0] = mutableListOf(turn)
            } else if (resultMap[lastNum]!!.size == 1) {
                lastNum = turn - resultMap[lastNum]!!.first() -1
                if (resultMap.containsKey(lastNum)) {
                    resultMap[lastNum]!!.add(turn)
                } else {
                    resultMap[lastNum] = mutableListOf(turn)
                }
            } else {
                val list = resultMap[lastNum]!!
                lastNum = list.last() - list[list.size-2]
                if (resultMap.containsKey(lastNum)) {
                    resultMap[lastNum]!!.add(turn)
                } else {
                    resultMap[lastNum] = mutableListOf(turn)
                }
            }
        } while (turn < maxIter);
        return lastNum
    }
}

