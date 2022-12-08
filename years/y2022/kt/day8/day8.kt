package y2022.kt.day8

import Solution

class Day8 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "21" to "8",
            "25" to "12"
        )

    override fun part1(input: List<String>): String {
        val width = input[0].length
        val height = input.size

        val trees = input.joinToString("")

        //The trees on the two sides and two top are visible, remove the corners
        var visible = width*2 + height*2 - 4

        println("StartHeight $visible")

        //Vertical loop
        for (y in 1 until height-1) {
            //Horizontal Loop
            for (x in 1 until width-1) {
                val idx = y*width + x

                val curTreeHeight = trees[idx].digitToInt()

                val topRows = x until idx step width
                val bottomRows = idx+width..(height*width) step width

                val leftRows = (y*width) until idx
                val rightRows = idx+1 until (((y+1)*width))

                /*if (isTest) {
                    println("Row: $y / Col: $x / H: $curTreeHeight")
                    println("\tTopRows: ${topRows.map{trees[it]}}")
                    println("\tBottomRows: ${bottomRows.map{trees[it]}}")
                    println("\tleftRows: ${leftRows.map{trees[it]}}")
                    println("\trightRows: ${rightRows.map{trees[it]}}")
                    println("=".repeat(25))
                }*/

                if (
                    listOf(topRows, bottomRows, leftRows, rightRows)
                        .any {
                            it.all {
                                trees.getOrElse(it) {'0'}.digitToInt() < curTreeHeight
                            }
                        }
                ) {
                    println("Is Higher $idx / $curTreeHeight")

                    visible++
                }
            }
        }

        return visible.toString()
    }

    override fun part2(input: List<String>): String {
        val width = input[0].length
        val height = input.size

        val trees = input.joinToString("").map{it.digitToInt()}

        var curHighest = 0

        //Vertical loop
        for (y in 1 until height-1) {
            //Horizontal Loop
            for (x in 1 until width-1) {
                val idx = y*width + x

                var scenicScore = 0

                val topRows = x..idx step width
                val bottomRows = idx..(height*width) step width

                val leftRows = (y*width)..idx
                val rightRows = idx until (((y+1)*width))

                listOf(
                    topRows.reversed(), bottomRows, leftRows.reversed(), rightRows
                ).map { list ->
                    list.map {trees[it]}
                }.forEach { list ->
                    list.reduce { prev, cur ->
                        if (prev == 11) return@reduce 11

                        if (cur <= prev) {
                            scenicScore++
                            return@reduce cur
                        }
                        return@reduce 11
                    }
                }

                if (curHighest < scenicScore) {
                    curHighest = scenicScore
                }

            }
        }

        return curHighest.toString()
    }
}
