package y2021.kt.day3

import Solution

class Day3: Solution() {

    override fun part1(input: List<String>): String {
        val gamma = List(input.first().length)
        { idx ->
            input
                .sumOf { (it[idx].digitToInt() * 2) -1}
                .coerceIn(0, 1)
        }
        return (gamma.joinToString("").toInt(2) * gamma.map{(it+1)%2}.joinToString("").toInt(2)).toString()
    }

    override fun part2(input: List<String>): String {
        val oxygen = input.map{it.map{it.digitToInt()}}.toMutableList()
        val cotwo = oxygen.toMutableList()
        for (i in 0 until oxygen[0].size) {
            if (oxygen.size > 1)
                oxygen.removeIf {it[i] != getCommon(oxygen.map{it[i]})}
            if (cotwo.size > 1)
                cotwo.removeIf {it[i] != getCommon(cotwo.map{it[i]}, true) }
        }
        return (oxygen[0].joinToString("").toInt(2) * cotwo[0].joinToString("").toInt(2)).toString()
    }

    private fun getCommon(nums: List<Int>, reverse: Boolean = false): Int {
        var res = nums.sumOf { (it * 2) - 1 }
        if (reverse) res *= -1

        return if (res == 0)
            if (reverse) 0 else 1
        else
            res.coerceIn(0,1)
    }
}
