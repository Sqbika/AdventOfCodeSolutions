package years.y2023.kt.day1

import common.Solution
import common.numberWordToInt

class Day1 : Solution() {

    val possibles = Regex("""(?=(one|two|three|four|five|six|seven|eight|nine|[0-9]))""")

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "142" to "142",
            "" to "281",
            "" to "1525",
            "" to "162"
        )

    override fun part1(input: List<String>): String = input.sumOf {
        it.filter { it.isDigit() }.run { "${first()}${last()}".toInt() }
    }.toString()

    override fun part2(input: List<String>): String = input.sumOf {
        possibles.findAll(it).map {it.groupValues}.flatten().filter {it.isNotBlank()}.run {
            val first = first().run {
                if (length > 1)
                    numberWordToInt()
                else
                    toInt()
            }
            val second = last().run {
                if (length > 1)
                    numberWordToInt()
                else
                    toInt()
            }

            if (isTest) println("$it: ${this.first()} / ${this.last()} | $first / $second | ${"$first$second".toInt()}")

            "$first$second".toInt()
        }
    }.toString()
}