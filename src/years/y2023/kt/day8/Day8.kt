package years.y2023.kt.day8

import common.Solution
import common.debugPrint
import common.lcm

class Day8 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "2" to "",
            "6" to "",
            "" to "6"
        )

    val mapline = Regex("""([0-9A-Z]{3}) = \(([0-9A-Z]{3}), ([0-9A-Z]{3})\)""")

    override fun part1(input: List<String>): String {
        val steps = input[0].toList()

        val map = input.drop(2).map {
            mapline.matchEntire(it)!!.groupValues.drop(1)
        }

        var head = map.find {it[0] == "AAA"}
        var counter = 0
        while (head!![0] != "ZZZ") {
            for (step in steps) {
                counter++
                head = map.find {
                    head!![if (step == 'L') 1 else 2] == it[0]
                }
                if (head!![0] == "ZZZ") {
                    break
                }
            }
        }

        return counter.toString()
    }

    override fun part2(input: List<String>): String {
        val steps = input[0].toList()

        val map = input.drop(2).map {
            mapline.matchEntire(it)!!.groupValues.drop(1)
        }

        var heads = map.filter {it[0].endsWith("A")}.debugPrint("[Heads] -> ")
        val periodity = mutableListOf<Long>()
        var counter = 0L
        while (heads.isNotEmpty()) {
            for (step in steps) {
                counter++
                val idx = if (step == 'L') 1 else 2
                heads = heads.map { head: List<String> ->
                    map.find { map: List<String> -> head[idx] == map[0] }!!
                }.filter {
                    if (it[0].endsWith("Z")) {
                        periodity.add(counter)
                        return@filter false
                    }
                    true
                }
            }
        }

        return periodity.lcm().toString()
    }
}