package y2022.kt.day5

import Solution
import transpose

class Day5 : Solution() {
    override val tests: List<Pair<String, String>>
        get() = listOf(
            "CMZ" to "MCD"
        )

    override fun part1(input: List<String>): String = solve(input, false)

    override fun part2(input: List<String>): String = solve(input, true)

    fun solve(input: List<String>, part2: Boolean = false): String {

        val sep = input.indexOf("")

        val cargo = input
            .subList(0, sep-1)
            .map { line -> line.chunked(4).map{it.trim().replace(Regex("[\\[\\]]*"), "")}}
            .transpose()
            .map {it.reversed().filterNotNull().filter{it != ""}.toMutableList()}

        val counter = cargo.map{it.size}.toMutableList()

        println(counter)
        println(cargo.transpose().joinToString("\n") {it.joinToString(" ") {it ?: "_"} })

        for (mov in input.subList(sep+1, input.size)) {
            println("=".repeat(25))
            println(mov)

            val parts = mov.split(" ")

            val amount = parts[1].toInt()
            val from = parts[3].toInt()-1
            val to = parts[5].toInt()-1

            counter[from] -= amount;
            counter[to] += amount;

            val removeStart = (cargo[from].size - amount)
            val toMove = cargo[from].subList(removeStart, cargo[from].size).toList()

            println("${removeStart} - ${toMove}")

            for (i in 0 until amount) {
                val removed = cargo[from].removeLast()
                if (!part2)
                    cargo[to].add(removed)
            }

            if (part2) cargo[to].addAll(toMove)

            println(" ".repeat(9).mapIndexed { idx, _ -> "${idx+1}" }.joinToString(" "))
            println(cargo.transpose().joinToString("\n") {it.joinToString(" ") {it ?: "_"} })

            counter.forEachIndexed { idx, cC ->
                if (cC != cargo[idx].size) {
                    throw Exception("well this is fucked up: Idx: ${idx+1}, Counter: ${cC}, Real: ${cargo[idx].size}")
                }
            }
        }

        return cargo.joinToString("") { it.lastOrNull().orEmpty() }
    }
}
