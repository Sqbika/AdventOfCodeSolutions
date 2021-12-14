package day19

import Solution
import java.math.BigInteger

class Day19(path:String) : Solution(path) {

    private fun getRules(input:List<String>):List<String> = input.joinToString("\n").split("\n\n")[0].split("\n")
    private fun getToValidate(input:List<String>):List<String> = input.joinToString("\n").split("\n\n")[1].split("\n")

    override fun part1() {
        val start = System.currentTimeMillis()
        println("Test1: ${solve(input)}")
        println("Test1 took: ${System.currentTimeMillis() - start} ms")
    }

    override fun part2() {
        val start = System.currentTimeMillis()
        println("Test2: ${solve(input, true)}")
        println("Test2 took: ${System.currentTimeMillis() - start} ms")
    }

    override fun test() {
        val start = System.currentTimeMillis()
        println("Test1: ${solve(test)}")
        println("Test1 took: ${System.currentTimeMillis() - start} ms")
        val start2 = System.currentTimeMillis()
        println("Test2: ${solve(test, true)}")
        println("Test2 took: ${System.currentTimeMillis() - start2} ms")
    }

    private fun solve(input: List<String>, part2:Boolean = false): BigInteger {
        val rules = getRules(input)
        val ruleMap: MutableMap<Int, Rule> = mutableMapOf()
        rules.forEach { rule ->
            val parts = rule.split(":")
            if (parts[1].contains("\"")) {
                ruleMap[parts[0].toInt()] = Rule(listOf(), parts[1][2], parts[0].toInt())
            } else {
                ruleMap[parts[0].toInt()] = Rule(parts[1].split("|").map { it2 -> it2.trim().split(' ').map { it.toInt() } }, ' ', parts[0].toInt())
            }
        }
        return getToValidate(input).map { str ->
            Regex("^" + ruleMap[0]!!.constructRegex(ruleMap, part2) + "$").matches(str)
        }.count {it}.toBigInteger()
    }
}


/*
0: 4 1 5
1: 2 3 | 3 2
2: 4 4 | 5 5
3: 4 5 | 5 4
4: "a"
5: "b"
 */
class Rule(
        var subRules:List<List<Int>> = listOf(),
        var char:Char = ' ',
        val id:Int = -1
) {
    fun isValid(input: String, ruleMap: Map<Int, Rule>): Boolean {
        return if (char != ' ') {
            input[0] == char
        } else {
            subRules.any { rules ->
                val chunks = input.chunked(input.length / rules.size)
                rules.mapIndexed {index, i ->
                    ruleMap[i]!!.isValid(chunks[index], ruleMap)
                }.all{it}
            }
        }
    }

    fun constructRegex(ruleMap: Map<Int, Rule>, part2:Boolean = false):String {
        if (char != ' ') {
            return char.toString()
        } else {
            return "(" + subRules.joinToString("|") { rules ->
                        if (id == 8 && part2) {
                            """(${ruleMap[42]!!.constructRegex(ruleMap, part2)}+?)"""
                        } else if (id == 11 && part2) {
                            "(?:" +
                                (1..5).joinToString("|") {
                                    "(?:" + ruleMap[42]!!.constructRegex(ruleMap, part2).repeat(it) + ruleMap[31]!!.constructRegex(ruleMap, part2).repeat(it) + ")"
                                } + ")"
                        } else {
                            "(" + rules.joinToString("") {
                                ruleMap[it]!!.constructRegex(ruleMap, part2)
                            } + ")"
                        }
                    } + ")"
        }
    }
}
