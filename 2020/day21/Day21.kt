package day21

import Solution
class Day21(path: String) : Solution(path) {

    override fun part1() {
        val start = System.currentTimeMillis()
        val result = solve(input)
        println("Part1&2 took: ${System.currentTimeMillis() - start} ms")
        println("Part1: ${result.first}")
        println("Part2: ${result.second}")
    }

    override fun part2() {
        //NOOP, solution in part1.
    }

    override fun test() {
        val start = System.currentTimeMillis()
        println("Test1: ${solve(test)}")
        println("Test1 took: ${System.currentTimeMillis() - start} ms")
        /*val start2 = System.currentTimeMillis()
        println("Test2: ${solvePart2(test)}")
        println("Test2 took: ${System.currentTimeMillis() - start2} ms")*/
    }

    private fun solve(input:List<String>) : Pair<Int, String> {
        val allergyMap : MutableMap<String, MutableList<String>> = mutableMapOf()
        val alllllErgies : MutableSet<String> = mutableSetOf()
        val alllingredients : MutableList<String> = mutableListOf()

        input.forEach { line ->
            val parts = line.split('(')
            val ingredients = parts[0].trim().split(' ')
            val allergies = parts[1].trim().replace(Regex("contains |\\)"), "").split(", ")
            alllllErgies.addAll(allergies)
            alllingredients.addAll(ingredients)
            for (ing in ingredients) {
                if (allergyMap.containsKey(ing)) {
                    allergyMap[ing]!!.addAll(allergies)
                } else {
                    allergyMap[ing] = allergies.toMutableList()
                }
            }
        }

        val reverseMap = allergyMap.toList().fold(mutableMapOf<String, MutableList<String>>()) { acc, pair ->
            pair.second.forEach {
                if (acc.containsKey(it))
                    acc[it]!!.add(pair.first)
                else
                    acc[it] = mutableListOf(pair.first)
            }
            acc
        }

    }
}
