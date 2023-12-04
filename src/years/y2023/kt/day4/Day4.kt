package years.y2023.kt.day4

import common.Solution
import common.pow

class Day4 : Solution() {

    val matcher = Regex("""Card ([ 0-9])*: ((?:[ 0-9]{1,2})*) \| ((?:[ 0-9]{1,2})*)""")

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "13" to "30"
        )

    override fun part1(input: List<String>): String =
        input
            .asSequence()
            .filter {it.isNotBlank()}
            .map {
                matcher.matchEntire(it)!!.run {
                    Triple(
                        groupValues[1].toInt(),
                        groupValues[2].split(" ").filter{ it.isNotBlank() }.map { it.trim().toInt() },
                        groupValues[3].split(" ").filter{ it.isNotBlank() }.map { it.trim().toInt() }
                    )
                }
            }.sumOf { (id, winningNumber, numbers) ->
                2.pow(numbers.filter { winningNumber.contains(it) }.size-1)
            }.toString()

    override fun part2(input: List<String>): String =
        input
            .asSequence()
            .filter {it.isNotBlank()}
            .map {
                matcher.matchEntire(it)!!.run {
                    Triple(
                        groupValues[1].toInt(),
                        groupValues[2].split(" ").filter{ it.isNotBlank() }.map { it.trim().toInt() },
                        groupValues[3].split(" ").filter{ it.isNotBlank() }.map { it.trim().toInt() }
                    )
                }
            }.foldIndexed(
                MutableList(input.filter{it.isNotBlank()}.size) {0}
            ) { idx, acc, (_, winningNumber, numbers) ->
                acc[idx] += 1
                for (i in 1..numbers.filter { winningNumber.contains(it) }.size) {
                    acc[idx+i] += acc[idx]
                }
                acc
            }.sum().toString()
}