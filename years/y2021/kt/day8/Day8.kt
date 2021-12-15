package y2021.kt.day8

import Solution
import swap
import kotlin.test.assertEquals

class Day8 : Solution()  {

    override fun part1(input: List<String>): String {
        return input.sumOf { SegMent.create(it).part1Sum()}.toString()
    }

    override fun part2(input: List<String>): String {
        val debugMap = input.map { SegMent.create(it).part2Sum()}

        return input.sumOf { SegMent.create(it).part2Sum()}.toString()
    }

    private class SegMent(
        val segments: List<String>,
        val outputs: List<String>
    ) {

        companion object {
            fun create(input: String): SegMent {
                val parts = input.split(" | ")
                return SegMent(parts[0].split(" "), parts[1].split(" "))
            }
        }

        fun part1Sum(): Int = outputs.filter {
            it.length in listOf(2,3,4,7)
        }.size

        fun part2Sum():Int {
            val sortedSegments = segments.map{it.toCharArray().sorted().joinToString("")}.toMutableList()

            //Set 1, 4, 7, 8
            for ((n, l) in listOf(Pair(1,2), Pair(7,3), Pair(4,4), Pair(8, 7))) {
                sortedSegments.swap(n, sortedSegments.indexOfFirst { it.length == l })
            }

            //set 6
            val charFreq = ('a'..'g').map { char ->
                Pair(char, sortedSegments.filter {it.contains(char)}.size)
            }

            val topRight = charFreq.find {
                it.second == 8 && sortedSegments[4].contains(it.first)
            }!!.first

            sortedSegments.swap(2, sortedSegments.indexOfFirst { !it.contains(charFreq.find { it.second == 9 }!!.first) })

            sortedSegments.swap(6, sortedSegments.indexOfFirst { !it.contains(topRight) && it.length == 6  })

            //set 9
            val bottomLeft = charFreq.find {it.second == 4}!!.first

            sortedSegments.swap(9, sortedSegments.indexOfFirst { it.length == 6 && !it.contains(bottomLeft) });

            //Set 0
            sortedSegments.swap(0, sortedSegments.indexOfFirst { it != sortedSegments[9] && it != sortedSegments[6] && it.length == 6 })

            //2, 3, 5
            //sortedSegments.swap(2, sortedSegments.indexOfFirst { it.length == 5 && it.contains(bottomLeft) })

            val topLeft = charFreq.find {it.second == 6}!!.first
            if (!sortedSegments[5].contains(topLeft))
                sortedSegments.swap(3,5)

            assertEquals(6, sortedSegments[0].length)
            assertEquals(2, sortedSegments[1].length)
            assertEquals(5, sortedSegments[2].length)
            assertEquals(5, sortedSegments[3].length)
            assertEquals(4, sortedSegments[4].length)
            assertEquals(5, sortedSegments[5].length)
            assertEquals(6, sortedSegments[6].length)
            assertEquals(3, sortedSegments[7].length)
            assertEquals(7, sortedSegments[8].length)
            assertEquals(6, sortedSegments[9].length)

            commonityCheck(sortedSegments)

            return outputs.map {
                sortedSegments.indexOf(it.toCharArray().sorted().joinToString(""))
            }.joinToString("").toInt()
        }

        fun commonityCheck(input: List<String>) {
            val commons = input.windowed(2).map {
                commons(it[0], it[1])
            }
            val commonVals = listOf(2, 1, 4, 3, 3, 5, 2, 3, 6)

            commons.zip(commonVals).forEach {
                assertEquals(it.first, it.second)
            }

            assertEquals(5, commons(input[0], input[9]))

        }

        fun commons(i1: String, i2: String) : Int = i1.toCharArray().filter {i2.contains(it)}.size
    }
}
