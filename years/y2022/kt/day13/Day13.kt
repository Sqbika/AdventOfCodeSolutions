package years.y2022.kt.day13

import common.Solution

class Signal(
    val input: String
) {
    val entries = mutableListOf<Any>()

    init {
        parseInput(input)
    }

    override fun toString(): String {
        return "[${entries.joinToString(",")}]"
    }

    private fun parseInput(input: String) {
        if (input == "") return

        val parts = mutableListOf<String>()

        var part = ""

        var partDepth = 0

        for (char in input) {
            when (char) {
                '[' -> {
                    partDepth++
                    part += char
                }
                ']' -> {
                    partDepth--
                    part += char
                }
                ',' -> {
                    if (partDepth == 0) {
                        parts.add(part)
                        part = ""
                    } else {
                        part += char
                    }
                }
                else -> part += char
            }
        }

        if (part.isNotEmpty()) {
            parts.add(part)
        }

        parts.forEach { part ->
            if (part.startsWith("[") && part.endsWith("]")) {
                entries.add(
                    Signal(
                        part.slice(1 until part.length-1)
                    )
                )
            } else {
                entries.add(
                    part.toInt()
                )
            }
        }
    }
}

class Day13 : Solution() {

    override val tests: List<Pair<String, String>>
            get() = listOf(
                "13" to "140"
            )

    override fun part1(input: List<String>): String {
        val parts = input.chunked(3) {
            it.take(2)
        }.map { signals ->
            signals.map {
                Signal(it.slice(1 until it.length-1))
            }
        }

        return parts.mapIndexed { idx, it ->
            Pair(idx, doCompare(it[0].entries, it[1].entries))
        }.onEach {
            println("${it.first+1}: ${it.second}")
        }.filter {
            it.second ?: throw Error("Uhhh?")
        }.sumOf { it.first+1 }.toString()
    }

    override fun part2(input: List<String>): String {
        val parts = input.plus(listOf("[[2]]", "[[6]]")).filter {
            it.isNotBlank()
        }.map {
            Signal(it.slice(1 until it.length-1))
        }.sortedWith { a, z ->
            val res = doCompare(a.entries, z.entries) ?: return@sortedWith 0

            if (res)
                return@sortedWith 1
            else
                return@sortedWith -1
        }.reversed()

        println(
            parts.joinToString("\n")
        )

        return ((parts.indexOfFirst { it.input == "[2]" }!!+1) * (parts.indexOfFirst { it.input == "[6]" }!!+ 1)).toString()
    }

    fun doCompare(left: List<Any>, right: List<Any>): Boolean? {
        println(
            "Comparing: [${left.joinToString(", ")}], [${right.joinToString(", ")}]"
        )

        for ((idx, lE) in left.withIndex()) {
            val rE = right.getOrNull(idx) ?: return false

            if (lE.javaClass != rE.javaClass) {
                val res = doCompare(
                    if (lE is Signal) lE.entries else listOf(lE),
                    if (rE is Signal) rE.entries else listOf(rE)
                )

                println("Comp: $lE - $rE = $res")

                if (res != null) return res
            } else {
                if (lE is Int && rE is Int) {
                    if (lE < rE) {
                        println("Comp: $lE - $rE = true")
                        return true
                    }
                    if (rE < lE) {
                        println("Comp: $lE - $rE = false")
                        return false
                    }
                    println("Comp: $lE - $rE = undefined")
                } else if (lE is Signal && rE is Signal) {
                    val res = doCompare(lE.entries, rE.entries)
                    println("Comp: $lE - $rE = $res")
                    if (res != null) return res
                } else {
                    throw Error("Uh?")
                }
            }
        }

        if (left.size < right.size) return true

        return null
    }
}
