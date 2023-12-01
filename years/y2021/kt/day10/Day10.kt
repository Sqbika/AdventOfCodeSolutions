package years.y2021.kt.day10

import common.Solution
import kotlin.math.max

class Day10: Solution() {

    override fun part1(input: List<String>): String = input.map{ ChunkSequence(it).run()}.sumOf { max(it, 0) }.toString()

    override fun part2(input: List<String>): String {
        val res = input.map {
            ChunkSequence(it).run(true)
        }

        return res.filter { it >= 0 }
            .sorted()
            .run {
                get(size/2)
            }.toString()
    }
}

class ChunkSequence(
    val seq: String
) {

    companion object {
        val closings = mapOf(
            ")" to 3L,
            "]" to 57L,
            "}" to 1197L,
            ">" to 25137L
        )

        val openings = mapOf(
            "(" to ")",
            "[" to "]",
            "{" to "}",
            "<" to ">"
        )

        val p2Points = mapOf(
            "(" to 1, "[" to 2, "{" to 3, "<" to 4
        )
    }

    fun run (runP2: Boolean = false): Long {
        val pieces = this.seq.toCharArray().map{it.toString()}.toList()

        val queue:ArrayDeque<String> = ArrayDeque()

        for (it in pieces) {
            if (openings.containsKey(it)) {
                queue.add(it)
            } else {
                val top = queue.removeLast()

                if (it != openings[top]) {
                    return if (!runP2)
                        closings[it]!!
                    else
                        -1
                }
            }
        }

        if (!runP2) return 0

        var newScore = 0L

        for (ch in queue.asReversed()) {
            newScore *= 5
            newScore += p2Points[ch]!!
        }

        return newScore
    }
}
