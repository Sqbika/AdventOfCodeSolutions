package y2022.kt.day11

import Solution
import productOf
import toDigits
import java.math.BigInteger
import kotlin.coroutines.coroutineContext

class Monke(
    val input: List<String>
) {
    var monkeId: Int = -1
    val items: MutableList<BigInteger> = mutableListOf()

    lateinit var op: List<String>

    var testFor: BigInteger = 0.toBigInteger()

    var ifTrue: Int = -1
    var ifFalse: Int = -1

    var inspections: Int = 0

    val threeBI = 3.toBigInteger()
    val zeroBI = 0.toBigInteger()

    var superMod = 1.toBigInteger()


    init {
        (0..5).forEach {
            val line = input[it]

            when (it) {
                0 -> {
                    monkeId = line.split(" ")[1].replace(":", "").toInt()
                }

                1 -> {
                    line.replace("  Starting items: ", "").split(", ").forEach {
                        items.add(it.toBigInteger())
                    }
                }

                2 -> {
                    op = line.replace("Operation: ", "").trim().split(" = ")[1].split(" ")
                }

                3 -> testFor = line.replace("Test: divisible by ", "").trim().toBigInteger()
                4 -> ifTrue = line.replace("If true: throw to monkey ", "").trim().toInt()
                5 -> ifFalse = line.replace("If false: throw to monkey ", "").trim().toInt()
            }
        }
    }

    fun doOp(monkes: List<Monke>, isPart1: Boolean = true) {
        if (items.isEmpty()) return

        val copy = items.toList()
        items.clear()

        inspections += copy.size

        copy.forEach { lvlArg ->
            var level = lvlArg
            
            if (!isPart1) {
                level %= superMod
            }

            val left = if (op[0] == "old") level else op[0].toBigInteger()
            val right = if (op[2] == "old") level else op[2].toBigInteger()

            var res = (when (op[1]) {
                "*" -> left * right
                "+" -> left + right
                "/" -> left / right
                "-" -> left - right
                else -> throw Error("Uhh, what? ${op[1]}")
            })

            if (isPart1) {
                res /= threeBI
            }

            if (res % testFor == zeroBI) {
                monkes[ifTrue].items.add(res)
            } else {
                monkes[ifFalse].items.add(res)
            }
        }
    }
}

class Day11 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "10605" to "2713310158"
        )

    override fun part1(input: List<String>): String {
        return runMonkeys(input, 20, true)
    }

    override fun part2(input: List<String>): String {
        return runMonkeys(input, 10000, false)
    }

    fun runMonkeys(input: List<String>, roundCount: Int, isPart2: Boolean): String {

        val logRounds = listOf(1, 20, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000)

        val monkes = input.chunked(7).map { Monke(it) }

        val superMod = monkes.fold(1.toBigInteger()) { acc, monke -> acc * monke.testFor}

        monkes.forEach { monke -> monke.superMod = superMod }

        (0 until roundCount).forEach {
            for (monke in monkes) {
                monke.doOp(monkes, isPart2)
            }

            if (isTest && logRounds.contains(it + 1)) {
                kotlin.run {
                    println("Round ${it + 1}")
                    monkes.forEach {
                        println("Monke ${it.monkeId}: ${it.inspections}")
                    }
                    println("=".repeat(25))
                }
            }
        }

        monkes.forEachIndexed { idx, m ->
            println("Monke ${m.monkeId}: ${m.inspections}")
        }

        return monkes.sortedByDescending { it.inspections }.take(2).productOf { it.inspections.toLong() }.toString()
    }

}