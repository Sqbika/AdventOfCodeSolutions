package day18

import common.Solution
import java.math.BigInteger

class Day18(path:String) : Solution(path) {

    override fun part1() {
        println("Part1: ${solve(input, ::parseMathPart1).toBigDecimal()}")
    }

    override fun part2() {
        println("Part2: ${solve(input, ::parseMathPart2).toBigDecimal()}")
    }

    override fun test() {
        println("Test1.1: ${solve(test, ::parseMathPart1)}")
        println("Test1.2: ${solve(listOf("1 + (2 * 3) + (4 * (5 + 6))"), ::parseMathPart1)}")
        println("Test1.3: ${solve(listOf("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"), ::parseMathPart1)}")
        println("Test2.1: ${solve(test, ::parseMathPart2)}")
    }

    private fun solve(input:List<String>, parser:((String) -> BigInteger)): BigInteger = input.map { line ->
        var toSolve = line
        val parenthesesRegex = Regex("\\(([^()]*)\\)")
        var matches = parenthesesRegex.findAll(toSolve).toList()
        while (matches.isNotEmpty()) {
            matches.forEach { s ->
                val result = parser.invoke(s.groupValues[1])
                toSolve = toSolve.replace(s.groupValues[0], result.toBigDecimal().toString())
            }
            matches = parenthesesRegex.findAll(toSolve).toList()
        }
        parser.invoke(toSolve)
    }.reduce { acc, bigInteger -> acc+bigInteger }


    private fun parseMathPart1(input:String): BigInteger {
        val parts = input.split(' ')
        var result = parts[0].toBigInteger()
        val add:((BigInteger, BigInteger) -> BigInteger) = BigInteger::plus
        val mult:((BigInteger, BigInteger) -> BigInteger) = BigInteger::times
        var curOp = add
        for (i in 1 until parts.size) {
            when (parts[i]) {
                "+" -> curOp = add
                "*" -> curOp = mult
                else -> {
                    result = curOp.invoke(result, parts[i].toBigInteger())
                }
            }
        }
        return result
    }

    private fun parseMathPart2(input:String): BigInteger {
        var toProcess = input
        val addRex = Regex("([0-9]* [+] [0-9]*)")
        var match:MatchResult? = addRex.find(toProcess)
        while (null != match) {
            toProcess = toProcess.replace(match.value, match.value.split(" + ").run { this[0].toBigInteger() + this[1].toBigInteger() }.toString())
            match = addRex.find(toProcess)
        }
        return toProcess.split(" * ").fold(1.toBigInteger()) { acc, s ->  acc * s.toBigInteger()}
    }
}
