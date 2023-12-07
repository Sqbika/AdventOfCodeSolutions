package years.y2023.kt.day7

import common.Solution
import common.debugPrint

class Day7 : Solution() {

    val ranks = "AKQJT98765432".reversed().toList()
    val p2Ranks = "AKQT98765432J".reversed().toList()

    fun toRank(card: String, rank: List<Char> = ranks) = card.toList().map {rank.indexOf(it)}

    fun groupCards(card: String) = card.toList().groupBy { it }.map {it.value.size}

    fun powerOfCard(card: String) = groupCards(card).run {
        when {
            this.contains(5) -> 1_000_000
            this.contains(4) -> 100_000
            this.contains(3) && this.contains(2) -> 10_000
            this.contains(3) -> 1_000
            this.count { it == 2 } == 2 -> 100
            this.contains(2) -> 10
            else -> 1
        }
    }

    fun solveTheJsPower(card: String) = p2Ranks.maxOf {
        powerOfCard(card.replace('J', it))
    }


    override val tests: List<Pair<String, String>>
        get() = listOf(
            "6440" to "5905"
        )

    override fun part1(input: List<String>): String =
        input.map {
            it.split(" ").run {
                Pair(get(0), get(1).toInt())
            }
        }.sortedWith { left, right ->
            val res = powerOfCard(left.first).compareTo(powerOfCard(right.first))

            if (res == 0) {
                return@sortedWith toRank(left.first).zip(toRank(right.first))
                    .first {
                        it.first.compareTo(it.second) != 0
                    }.run {
                        first.compareTo(second)
                    }
            }

            return@sortedWith res
        }.mapIndexed { idx, it ->
            it.second * (idx+1)
        }.sum().toString()

    override fun part2(input: List<String>): String =
        input.map {
            it.split(" ").run {
                Pair(get(0), get(1).toInt())
            }
        }.sortedWith { left, right ->
            val res = solveTheJsPower(left.first).compareTo(solveTheJsPower(right.first))

            if (res == 0) {
                return@sortedWith toRank(left.first, p2Ranks).zip(toRank(right.first, p2Ranks))
                    .first {
                        it.first.compareTo(it.second) != 0
                    }.run {
                        first.compareTo(second)
                    }
            }

            return@sortedWith res
        }.debugPrint().mapIndexed { idx, it ->
            it.second * (idx+1)
        }.sum().toString()
}
