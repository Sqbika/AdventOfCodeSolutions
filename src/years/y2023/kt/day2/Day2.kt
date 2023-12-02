package years.y2023.kt.day2

import common.Solution
import common.max
import common.productOf

class Day2 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "8" to "2286"
        )

    val post = listOf("red", "green", "blue")
    val part1Config = listOf(12, 13, 14)
    val cubeRegex = Regex("""([0-9]*) (red|green|blue)""")

    override fun part1(input: List<String>): String = input.mapNotNull { line ->
        val parts = line.split(':', limit = 2)
        val gameId = parts[0].substring(5)

        if (cubeRegex.findAll(parts[1]).any {
            it.groupValues[1].toInt() > part1Config[post.indexOf(it.groupValues[2])]
        }) {
            return@mapNotNull null
        }


        return@mapNotNull gameId.toInt()
    }.sum().toString()

    override fun part2(input: List<String>): String = input.sumOf { line ->
        val parts = line.split(':', limit = 2)

        cubeRegex.findAll(parts[1]).groupBy({
            it.groupValues[2]
        }) {
            it.groupValues[1].toInt()
        }.values.map {
            it.max()
        }.productOf()
    }.toString()
}


