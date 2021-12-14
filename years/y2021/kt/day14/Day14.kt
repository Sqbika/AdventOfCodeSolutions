package y2021.kt.day14

import Solution
import kotlinx.coroutines.runBlocking
import pmap

class Day14(path: String) : Solution(path) {

    override fun part1(input: List<String>) {
        runBlocking {
            println("Part 1: ${solve(input, 10)}")
        }
    }

    override fun part2(input: List<String>) {
        runBlocking {
            println("Part 2: ${solve(input, 40)}")
        }
    }

    fun solve(input:List<String>, count: Int):Long {

        var start = input[0].windowed(2).groupingBy { it }.eachCount().map {
            it.key to it.value.toLong()
        }.toMap()

        val pairs = input.subList(2, input.size).map {
            it.split(" -> ")
        }.associate {
            it[0] to it[1]
        }

        for (i in 0 until count) {
            val newCount = mutableMapOf<String, Long>()

            start.keys.forEach {
                if (pairs.containsKey(it)) {
                    val newChar = pairs[it]!!

                    listOf(it[0] + newChar, newChar + it[1]).forEach { newChar ->
                        if (newCount.containsKey(newChar)) {
                            newCount[newChar] = newCount[newChar]!! + start[it]!!
                        } else {
                            newCount[newChar] = start[it]!!.toLong()
                        }
                    }
                } else {
                    if (newCount.containsKey(it)) {
                        newCount[it] = newCount[it]!! + start[it]!!
                    } else {
                        newCount[it] = start[it]!!.toLong()
                    }
                }
            }

            start = newCount
        }

        val result: Long = start
            .toList()
            .map { pair: Pair<String, Long> ->
                pair.first.toList().map {
                    it to pair.second
                }
            }
            .flatten()
            .groupBy { it.first }
            .map {
                it.key to it.value.sumOf { it.second }
            }
            .toList()
            .run {
                maxOf{it.second} - minOf{it.second}
            } / 2 + (start.size % 2).toLong()

        return result
    }
}
