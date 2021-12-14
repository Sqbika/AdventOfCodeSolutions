package y2021.kt.day11

import Solution
import kotlin.math.max
import kotlin.math.min

class Day11(path: String) : Solution(path) {

    override fun part1(input: List<String>) {
        doit(input)
    }

    override fun part2(input: List<String>) {
        doit(input, true)
    }

    fun print(input: List<List<Int>>) {
        println(
            input.joinToString("\n") {
                it.map { if (it == -1) "X" else it }.joinToString("")
            }
        )
    }

    fun updateCircled(nums: MutableList<MutableList<Int>>, y:Int, x:Int) {
        if (nums[y][x] != -1) {
            if (nums[y][x] > 9) {
                nums[y][x] = -1
                for (y2 in max(0, y - 1)..min(y + 1, nums.size - 1)) {
                    for (x2 in max(0, x - 1)..min(x + 1, nums[y2].size - 1)) {
                        if (nums[y2][x2] != -1) {
                            nums[y2][x2]++

                            if (nums[y2][x2] > 9) {
                                updateCircled(nums, y2, x2)
                            }
                        }
                    }
                }
            }
        }
    }

    fun doit(input: List<String>, part2:Boolean = false) {
        val nums = input.map { it.toCharArray().map { it.digitToInt() }.toMutableList() }.toMutableList()

        var flashCount = 0

        val steps = if (part2) 100_000_000 else 100

        for (i in 0 until steps) {
            for (y in nums.indices) {
                for (x in nums[y].indices) {
                    nums[y][x]++
                }
            }

            for (y in nums.indices) {
                for (x in nums[y].indices) {
                    if (nums[y][x] > 9) {
                        updateCircled(nums, y, x)
                    }
                }
            }

            println("Cycle $i:")
            print(nums)

            if (part2) {
                if (nums.all {
                    it.all {it == -1}
                }) {
                    println("Part 2: ${i+1}")
                    return
                }
            }

            nums.forEach {
                it.replaceAll {
                    if (it == -1) {
                        flashCount++
                        0
                    } else it
                }
            }
        }

        println("Part 1: $flashCount")
    }
}
