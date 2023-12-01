package years.y2021.kt.day21

import common.Solution
import java.math.BigInteger
import kotlin.math.max

class Day21 : Solution() {

    override val tests: List<Pair<String, String>> = listOf(
        Pair("739785", "444356092776315")
    )

    override fun part1(input: List<String>): String {
        var p1 = 0
        var point1 = 0
        var p2 = 0
        var point2 = 0

        input.map {
            it.split(" ")[4].toInt()
        }.run {
            p1 = get(0)
            p2 = get(1)
        }

        var die = 0

        fun roll() {
            die += 3
            if (die % 2 == 0) {
                p2 = (die - 2 + die - 1 + die - 1 + p2) % 10 + 1
                point2 += p2
            } else {
                p1 = (die - 2 + die - 1 + die - 1 + p1) % 10 + 1
                point1 += p1
            }
        }

        while (point1 < 1000 && point2 < 1000) {
            roll()
        }

        return if (point1 < 1000) {
            (point1 * die).toString()
        } else {
            (point2 * die).toString()
        }

    }

    override fun part2(input: List<String>): String {
        var p1 = 0
        var p2 = 0

        var p1Wins = BigInteger.ZERO
        var p2Wins = BigInteger.ZERO

        input.map {
            it.split(" ")[4].toInt()
        }.run {
            p1 = get(0)
            p2 = get(1)
        }

        var gameMap = mapOf<Game, BigInteger>(
            Game(p1, p2) to BigInteger.ONE
        )

        var isP2 = false

        while (gameMap.isNotEmpty()) {
            val newMap = mutableMapOf<Game, BigInteger>()

            for ((headGame, headCount) in gameMap) {
                repeat(3) { a ->
                    repeat(3) { b ->
                        repeat(3) { c ->
                            val pa = headGame.newGame(a+b+c+3, isP2)

                            if (pa.point1 >= 21) {
                                p1Wins += headCount
                            } else if (pa.point2 >= 21) {
                                p2Wins += headCount
                            } else {
                                newMap[pa] = headCount + (newMap[pa] ?: BigInteger.ZERO)
                            }
                        }
                    }
                }
            }

            gameMap = newMap
            isP2 = !isP2
        }


        return (if (p1Wins > p2Wins) p1Wins else p2Wins).toString()
    }
}

data class Game(
    var p1: Int,
    var p2: Int,
    var point1: Int = 0,
    var point2: Int = 0
) {

    fun newGame(n: Int, isP2: Boolean): Game {
        return if (isP2) {
            Game(
                p1,
                (p2 + n - 1) % 10 + 1,
                point1,
                point2 + (p2 + n - 1) % 10 + 1
            )
        } else {
            Game(
                (p1 + n - 1) % 10 + 1,
                p2,
                point1 + (p1 + n - 1) % 10 + 1,
                point2,
            )
        }
    }
}
