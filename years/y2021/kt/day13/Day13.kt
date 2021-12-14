package y2021.kt.day13

import Solution

class Day13(path: String) : Solution(path) {

    override fun part1(input: List<String>) {
        println("Part 1: ${solve(input)}")
    }

    override fun part2(input: List<String>) {
        println("Part 2: ${solve(input, true)}")
    }

    fun visualize(pairs: List<Pair<Int, Int>>) {
        val maxX = pairs.maxOf {it.first}
        val maxY = pairs.maxOf {it.second}

        println(
            List(maxY+1) { y ->
                List(maxX+1) { x ->
                    if (pairs.contains(Pair(x,y))) {
                        "#"
                    } else
                        " "
                }.joinToString("")
            }.joinToString("\n")
        )
    }

    fun solve(input: List<String>, part2: Boolean = false): Int {
        val segment = input.indexOf("")

        //Pair(x,y)
        val positions = input.subList(0, segment).map {
            it.split(",").run {
                Pair(this[0].toInt(), this[1].toInt())
            }
        }.toMutableList()

        val folds = input.subList(segment+1, input.size).map {
            it.split(" ")[2].split("=").run {
                Pair(this[0], this[1].toInt())
            }
        }


        if (part2)
            folds.forEach { fold ->
                fold(fold, positions)
            }
        else
            fold(folds[0], positions)

        visualize(positions)

        return positions.map {
            "${it.first},${it.second}"
        }.distinct().size
    }

    fun fold (fold: Pair<String, Int>, positions: MutableList<Pair<Int,Int>>) {
        positions.replaceAll {
            if (fold.first == "x") {
                if (it.first > fold.second) {
                    Pair(fold.second - (it.first - fold.second), it.second)
                } else {
                    it
                }
            } else  if (fold.first == "y") {
                if (it.second > fold.second) {
                    Pair(it.first, fold.second - (it.second - fold.second))
                } else {
                    it
                }
            } else {
                it
            }
        }
    }
}
