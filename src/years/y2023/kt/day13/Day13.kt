package years.y2023.kt.day13

import common.Solution
import common.debugPrint
import common.stringDiff
import common.transposeStrings

data class MatchHolder(
    val idx: Int,
    val left: String,
    val right: String,
    val hadFix: Boolean = false
) {
    fun toHadFix() = this.copy(hadFix = true)

    fun getRange(max: Int) = IntRange(0, idx).reversed().zip(IntRange(idx + 1, max))
}

class Day13 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "405" to "400"
        )

    fun numOfMatching(strings: List<String>): Int {
        val pairs = strings.zipWithNext()
        val vertical = mutableListOf<Pair<Int, Pair<String, String>>>()
        pairs.forEachIndexed { idx, it ->
            if (it.first == it.second) {
                vertical.add(Pair(idx, it))
            }
        }
        if (vertical.isNotEmpty()) {
            return vertical.maxOf {
                val idx = it.first
                if (IntRange(0, idx).reversed().zip(IntRange(idx + 1, strings.size - 1)).all { (left, right) ->
                        strings[left] == strings[right]
                    }) {
                    idx + 1
                } else {
                    0
                }
            }
        }

        return 0
    }

    fun findAllPart2(strings: List<String>): Int {
        val zipped = strings.zipWithNext()
        val foundPairs = mutableListOf<MatchHolder>()

        for (idx in zipped.indices) {
            val (left, right) = zipped[idx]
            val diffCount = left.stringDiff(right)

            if (diffCount <= 1) foundPairs.add(MatchHolder(idx, left, right))
        }

        if (foundPairs.isNotEmpty()) {
            indiciesFor@ for (idx in foundPairs.indices) {
                var holder = foundPairs[idx]
                val ranges = holder.getRange(strings.size -1)

                for ((leftIdx, rightIdx) in ranges) {
                    val left = strings[leftIdx]
                    val right = strings[rightIdx]

                    if (left == right) continue
                    if (holder.hadFix) continue@indiciesFor

                    if (left.stringDiff(right) == 1) {
                        holder = holder.toHadFix()
                        continue
                    }
                }

                return holder.idx + 1
            }
        }

        return 0
    }

    override fun part1(input: List<String>): String =
        input.joinToString("\n")
            .split("\n\n")
            .map { it.split("\n") }
            .sumOf { strings ->
                println("=====")
                strings.joinToString("\n").debugPrint()
                val result = numOfMatching(strings.transposeStrings())
                if (result != 0) return@sumOf result.debugPrint()

                (numOfMatching(strings) * 100).debugPrint()
            }.toString()


    override fun part2(input: List<String>): String =
    input.joinToString("\n")
        .split("\n\n")
        .map { it.split("\n") }
        .sumOf { strings ->
            println("=====")
            strings.joinToString("\n").debugPrint()
            val result = findAllPart2(strings) * 100
            if (result != 0) return@sumOf result.debugPrint()

            (findAllPart2(strings.transposeStrings())).debugPrint()
        }.toString()
}