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

        val allOfthem = alllllErgies.map { allergy ->
            Pair(allergy, alllingredients.toSet().mapNotNull { food ->
                if (allergyMap.containsKey(food))
                    Pair(food, allergyMap[food]!!.count { it == allergy } )
                else
                    null
            }.sortedBy { it.second }.reversed())
        }

        val result2 = alllllErgies

        val pool = alllingredients.toMutableSet()
        val result = alllllErgies.map { allergy ->
            val num = pool.map { food ->
                    Pair(food, allergyMap[food]!!.count { it == allergy })
                }.maxByOrNull { it.second }!!
            pool.remove(num.first)
            Pair(allergy, num)
        }



        val sortedAsd = result
                .sortedBy { it.first }

        return Pair(pool.map { ing ->
            alllingredients.count { ing == it }
        }.sum(), sortedAsd.joinToString(",") { it.second.first })
    }


}
