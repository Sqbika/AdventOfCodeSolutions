package years.y2024.kt.day4

import common.Solution

class Day4 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "18" to "9"
        )

    override fun part1(input: List<String>): String {
        val lines = input.map { it.toCharArray() }

        var count = 0

        val offSets = listOf(
            listOf(0, 0, 0) to listOf(1, 2, 3),
            listOf(0, 0, 0) to listOf(-1, -2, -3),
            listOf(1, 2, 3) to listOf(0, 0, 0),
            listOf(-1, -2, -3) to listOf(0, 0, 0),
            listOf(1, 2, 3) to listOf(1, 2, 3),
            listOf(1, 2, 3) to listOf(-1, -2, -3),
            listOf(-1, -2, -3) to listOf(1, 2, 3),
            listOf(-1, -2, -3) to listOf(-1, -2, -3)
        )

        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == 'X') {
                    offSets.forEach { offset ->
                        val indicies = offset.first.zip(offset.second).map { it.first + y to it.second + x }
                        if (isXMAS(lines, indicies)) {
                            count++
                        }
                    }
                }
            }
        }

        return count.toString()
    }


    override fun part2(input: List<String>): String {
        val lines = input.map { it.toCharArray() }

        var count = 0

        fun isChar(y: Int, x: Int, char: Char) = lines.getOrNull(y)?.getOrNull(x) == char

        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == 'A') {
                    //TODO Very ugly lazy solution, replace with normal pls
                    if (
                        (
                            isChar(y-1, x-1, 'M') && isChar(y-1, x+1, 'M') &&
                            isChar(y+1, x-1, 'S') && isChar(y+1, x+1, 'S')
                        ) || (
                            isChar(y-1, x-1, 'M') && isChar(y-1, x+1, 'S') &&
                            isChar(y+1, x-1, 'M') && isChar(y+1, x+1, 'S')
                        ) || (
                            isChar(y-1, x-1, 'S') && isChar(y-1, x+1, 'S') &&
                            isChar(y+1, x-1, 'M') && isChar(y+1, x+1, 'M')
                        ) || (
                            isChar(y-1, x-1, 'S') && isChar(y-1, x+1, 'M') &&
                            isChar(y+1, x-1, 'S') && isChar(y+1, x+1, 'M')
                        )
                    ) {
                        count++
                    }
                }
            }
        }

        return count.toString()
    }

    fun isXMAS(lines: List<CharArray>, indexes: List<Pair<Int, Int>>): Boolean {
        if (indexes.size != 3) {
            throw Exception("indexes should have 3 indicies!")
        }

        val chars = listOf('M', 'A', 'S')

        for (i in indexes.indices) {
            val pair = indexes[i]

            if (lines.getOrNull(pair.first)?.getOrNull(pair.second) != chars[i]) {
                return false
            }
        }

        return true
    }
}