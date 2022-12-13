package day9

import common.Solution
import java.util.*
import kotlin.test.assertEquals

class Day9(path: String) : Solution(path) {

    override fun part1() {
        val nums = input.map {it.toLong()}
        solvePart1(nums, 25)
    }

    override fun part2() {
        val nums = input.map {it.toLong()}
        val result = solvePart2(nums, solvePart1(nums, 25)).sorted()
        println("Part2 result: ${result.first() + result.last()}")
    }

    override fun test() {
        val nums = test.map {it.toLong()}
        assertEquals(solvePart1(nums, 5), 127)
        println("Part1 tested.")
        assertEquals(solvePart2(nums, 127), listOf(15L, 25L, 47L, 40L))
        println("Part2 tested.")
    }

    private fun solvePart1(nums:List<Long>, poolSize: Int):Long {
        val pool = LinkedList(nums.slice(0 until poolSize))
        var idx = poolSize
        while(true) {
            val ptr = nums[idx]
            val pair = findPair(pool, ptr)
            if (pair != null) {
                pool.remove()
                pool.add(ptr)
            } else {
                println("Found the number: $ptr")
                return ptr
            }
            assertEquals(pool.size, poolSize)
            idx++
        }
    }

    private fun findPair(pool: Collection<Long>, nextNum:Long ): Pair<Long,Long>? {
        pool.forEachIndexed {iX, x ->
            pool.forEachIndexed {iY, y ->
                if (x+y == nextNum && iX != iY)
                return Pair(x,y)
            }
        }
        return null
    }

    private fun solvePart2(nums:List<Long>, numberToSearch:Long):List<Long> {
        var idx = 0; var ptr = 0
        while(idx < nums.size-2) {
            val sum = nums.subList(idx, ptr+1).sum()
            if (sum < numberToSearch) {
                ptr++
                continue
            }
            if (sum > numberToSearch) {
                idx++
                ptr = idx
                continue
            }
            if (sum == numberToSearch) {
                val result = nums.subList(idx, ptr+1)
                println("Found the numbers: [${result.joinToString(",")}]")
                return result
            }
        }
        println("uhoh found nothing...")
        return listOf()
    }

}
