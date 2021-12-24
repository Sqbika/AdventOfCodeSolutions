package y2021.kt.day22

import Solution

class Day22 : Solution() {

    override val tests: List<Pair<String, String>> = listOf(
        Pair("39", ""),
        Pair("590784", "")
    )

    override fun part1(input: List<String>): String {
        val cubes = mutableMapOf<Triple<Int,Int,Int>, Boolean>()

        getLines(input).forEach { it.toggleCubes(cubes) }

        return cubes.values.count { it }.toString()
    }

    override fun part2(input: List<String>): String {

        return ""
    }

    fun getLines(input: List<String>): List<Line> = input.map { line ->
        val onOff = line.split(" ")[0] == "on"

        val positions = line.split(" ")[1].split(",").map { range ->
            range.split("=")[1].split("..").run {
                IntRange(get(0).toInt(), get(1).toInt())
            }
        }

        Line(
            onOff,
            positions[0],
            positions[1],
            positions[2],
        )
    }

}

data class Line(
    val onOff: Boolean,
    val xRange: IntRange,
    val yRange: IntRange,
    val zRange: IntRange
) {

    companion object {
        val fiftyRange = IntRange(-50, 50)
    }

    fun toggleCubes(cubeMap: MutableMap<Triple<Int,Int,Int>, Boolean>, skipfifty: Boolean = true) {
        xRange.forEach { x ->
            if (!skipfifty || fiftyRange.contains(x))
                yRange.forEach { y ->
                    if (!skipfifty || fiftyRange.contains(y))
                        zRange.forEach { z ->
                            if (!skipfifty || fiftyRange.contains(z))
                                cubeMap[Triple(x,y,z)] = onOff
                        }
                }
        }
    }
}

class SegmentedRange {
    val ranges = sortedSetOf<IntRange>()

}

class SortedRange(
    val start: Int,
    val end: Int
) {


    fun split(startEnd: Int, endStart: Int = startEnd): List<SortedRange> = listOf(SortedRange(start, startEnd), SortedRange(endStart, end))

    /*fun combine(other: SortedRange): SortedRange {
        if (this.start > other.end || other.start > this.end)
    }*/
}
