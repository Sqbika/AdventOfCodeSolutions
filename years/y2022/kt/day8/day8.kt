package years.y2022.kt.day8

import common.Solution
import common.takeUntil

class Day8 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "21" to "8",
            //"25" to "12"
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

                val curTreeHeight = trees[idx]

                val topRows = x until idx step width
                val bottomRows = idx+width..(height*width) step width

                val leftRows = (y*width) until idx
                val rightRows = idx+1 until (((y+1)*width))

                val sizeMap = listOf(
                    topRows.reversed(), bottomRows, leftRows.reversed(), rightRows
                ).map { list ->
                    list.map {
                        trees[it]
                    }.takeUntil {
                        it < curTreeHeight
                    }.size
                }

                if (isTest) {
                    println("=".repeat(25))
                    println("top, bottom, left, right")
                    println(
                        listOf(topRows.reversed(), bottomRows, leftRows.reversed(), rightRows)
                            .map {it.toList().map {
                                trees[it]
                            }}
                            .joinToString("\n")
                    )

                    println("map")
                    println(
                        (0 until height).joinToString("\n") { y2 ->
                            (0 until width).joinToString("") { x2 ->
                                if (x == x2 || y == y2)
                                    trees[y2*width + x2].toString()
                                else
                                    " "
                            }
                        }
                    )

                    val asd =
                        listOf(
                            topRows.reversed(), bottomRows, leftRows.reversed(), rightRows
                        ).map { list ->
                            list.map {
                                trees[it]
                            }.takeUntil {
                                it < curTreeHeight
                            }
                        }

                    println("filtered")
                    println(asd.joinToString("\n"))

                    println("score")
                    println(asd.fold(1) {acc, i -> acc * i.size})
                }

                val scenicScore = sizeMap.reduce { acc, i ->  acc * i}
                //println("Row: $y / Col: $x / H: $curTreeHeight / SS: $scenicScore / ${sizeMap}")

                if (curHighest < scenicScore) {
                    curHighest = scenicScore
                }

            }
        }

        return curHighest.toString()
    }
}
