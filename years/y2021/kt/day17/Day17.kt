package y2021.kt.day17

import Solution
import kotlin.math.absoluteValue

class Day17 : Solution() {

    override val tests: List<Pair<String, String>> = listOf(
        Pair("45", "112")
    )

    override fun part1(input: List<String>): String {
        val range = getRange(input[0])

        var highestY = 0

        for (xVelo in 0..range.first.last) {
            for (yVelo in 0..range.second.first.absoluteValue*3) {
                val probe = Probe(range, xVelo, yVelo)
                val result = probe.run()
                println("Trying X:$xVelo/Y:$yVelo. Result: ${result}")
                if (result == ProbeResult.HIT) {
                    if (highestY < probe.highestY) highestY = probe.highestY
                }
            }
        }


        return highestY.toString()
    }

    override fun part2(input: List<String>): String {
        val range = getRange(input[0])

        val hitVelos = mutableSetOf<Pair<Int,Int>>()


        for (xVelo in 0..range.first.last) {
            for (yVelo in (range.second.first*3)..range.second.last.absoluteValue*3) {
                val probe = Probe(range, xVelo, yVelo)
                val result = probe.run()
                println("Trying X:$xVelo/Y:$yVelo. Result: ${result}")
                if (result == ProbeResult.HIT) {
                    hitVelos.add(Pair(xVelo, yVelo))
                }
            }
        }

        return hitVelos.size.toString()
    }

    private fun getRange(input: String): Pair<IntRange, IntRange> = input.substring("target area: ".length).split(", ").run {
            map {
                it.split("..").run {
                    listOf(get(0).substring(2).toInt(), get(1).toInt())
                }
            }.run {
                val xRange = get(0).sorted().run {
                    IntRange(get(0), get(1))
                }
                val yRange = get(1).sorted().run {
                    IntRange(get(0), get(1))
                }

                Pair(xRange, yRange)
            }
    }
}

class Probe (
    val range: Pair<IntRange, IntRange>,
    var xVelo: Int,
    var yVelo: Int
) {
    var xPos = 0
    var yPos = 0

    var highestY = 0

    fun run(): ProbeResult {
        while(true) {
            xPos += xVelo
            updateXVelo()
            yPos += yVelo
            updateY()

            if (yPos > highestY) highestY = yPos

            if (range.first.contains(xPos) && range.second.contains(yPos)) return ProbeResult.HIT

            if (range.first.last < xPos || range.second.first > yPos) return ProbeResult.NO_HIT
        }
    }

    private fun updateXVelo() {
        if (xVelo != 0) {
            if (xVelo < 0) {
                xVelo += 1
            } else {
                xVelo -= 1
            }
        }
    }

    private fun updateY() {
        yVelo -= 1
        if (highestY < yPos) highestY = yPos
    }

}

enum class ProbeResult {
    NO_HIT,
    HIT
}
